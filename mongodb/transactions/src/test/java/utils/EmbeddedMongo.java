/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package utils;

import de.flapdoodle.embed.mongo.config.IMongoCmdOptions;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.IMongosConfig;
import de.flapdoodle.embed.mongo.config.MongoCmdOptionsBuilder;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.MongosConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Feature;
import de.flapdoodle.embed.mongo.distribution.IFeatureAwareVersion;
import de.flapdoodle.embed.mongo.distribution.Versions;
import de.flapdoodle.embed.mongo.tests.MongosSystemForTestFactory;
import de.flapdoodle.embed.process.distribution.GenericVersion;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * @author Christoph Strobl
 */
public class EmbeddedMongo extends ExternalResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedMongo.class);

	private static final String LOCALHOST = "127.0.0.1";
	private static final String DEFAULT_REPLICA_SET_NAME = "rs0";
	private static final String DEFAULT_CONFIG_SERVER_REPLICA_SET_NAME = "rs-config";

	private static final String STORAGE_ENGINE = "wiredTiger";

	private static final IFeatureAwareVersion VERSION = Versions.withFeatures(new GenericVersion("3.7.9"),
			Feature.ONLY_WITH_SSL, Feature.ONLY_64BIT, Feature.NO_HTTP_INTERFACE_ARG, Feature.STORAGE_ENGINE,
			Feature.MONGOS_CONFIGDB_SET_STYLE, Feature.NO_CHUNKSIZE_ARG);

	private final TestResource resource;

	private EmbeddedMongo(TestResource resource) {
		this.resource = resource;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder replSet() {
		return replSet(DEFAULT_REPLICA_SET_NAME);
	}

	public static Builder replSet(String replicaSetName) {
		return new Builder().withReplicaSetName(replicaSetName);
	}

	public static class Builder {

		IFeatureAwareVersion version;
		String replicaSetName;
		List<Integer> serverPorts;
		List<Integer> configServerPorts;

		Builder() {

			version = VERSION;
			replicaSetName = null;
			serverPorts = Collections.emptyList();
			configServerPorts = Collections.emptyList();
		}

		public Builder withVersion(IFeatureAwareVersion version) {

			this.version = version;
			return this;
		}

		public Builder withReplicaSetName(String replicaSetName) {

			this.replicaSetName = replicaSetName;
			return this;
		}

		public Builder withServerPorts(Integer... ports) {

			this.serverPorts = Arrays.asList(ports);
			return this;
		}

		public EmbeddedMongo configure() {

			if (serverPorts.size() > 1 || StringUtils.hasText(replicaSetName)) {

				String rsName = StringUtils.hasText(replicaSetName) ? replicaSetName : DEFAULT_REPLICA_SET_NAME;
				return new EmbeddedMongo(new ReplSet(version, rsName, serverPorts.toArray(new Integer[serverPorts.size()])));
			}

			throw new UnsupportedOperationException("implement me");
		}

	}

	@Override
	protected void before() throws Throwable {
		resource.start();
	}

	@Override
	protected void after() {
		resource.stop();
	}

	public MongoClient getMongoClient() {
		return resource.mongoClient();
	}

	public String getConnectionString() {
		return resource.connectionString();
	}

	private static Integer randomOrDefaultServerPort() {

		try {
			return Network.getFreeServerPort();
		} catch (IOException e) {
			return 27017;
		}
	}

	interface TestResource {

		void start();

		void stop();

		String connectionString();

		default MongoClient mongoClient() {
			return new MongoClient(new MongoClientURI(connectionString()));
		}
	}

	static class ReplSet implements TestResource {

		private static final String DEFAULT_SHARDING = "none";
		private static final String DEFAULT_SHARD_KEY = "_class";

		private final IFeatureAwareVersion serverVersion;
		private final String configServerReplicaSetName;
		private final String replicaSetName;
		private final int mongosPort;
		private final Integer[] serverPorts;
		private final Integer[] configServerPorts;

		private MongosSystemForTestFactory mongosTestFactory;

		ReplSet(IFeatureAwareVersion serverVersion, String replicaSetName, Integer... serverPorts) {

			this.serverVersion = serverVersion;
			this.replicaSetName = replicaSetName;
			this.serverPorts = defaultPortsIfRequired(serverPorts);
			this.configServerPorts = defaultPortsIfRequired(null);
			this.configServerReplicaSetName = DEFAULT_CONFIG_SERVER_REPLICA_SET_NAME;
			this.mongosPort = randomOrDefaultServerPort();
		}

		Integer[] defaultPortsIfRequired(Integer[] ports) {

			if (!ObjectUtils.isEmpty(ports)) {
				return ports;
			}

			try {
				return new Integer[] { Network.getFreeServerPort(), Network.getFreeServerPort(), Network.getFreeServerPort() };
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void start() {

			if (mongosTestFactory != null) {
				return;
			}

			doStart();
		}

		private void doStart() {

			Map<String, List<IMongodConfig>> replicaSets = new LinkedHashMap<>();
			replicaSets.put(configServerReplicaSetName, initConfigServers());
			replicaSets.put(replicaSetName, initReplicaSet());

			// create mongos
			IMongosConfig mongosConfig = defaultMongosConfig(serverVersion, mongosPort, defaultCommandOptions(),
					configServerReplicaSetName, configServerPorts[0]);

			mongosTestFactory = new MongosSystemForTestFactory(mongosConfig, replicaSets, Collections.emptyList(),
					DEFAULT_SHARDING, DEFAULT_SHARDING, DEFAULT_SHARD_KEY);

			try {
				LOGGER.info(String.format("Starting config servers at ports %s",
						StringUtils.arrayToCommaDelimitedString(configServerPorts)));
				LOGGER.info(String.format("Starting replica set '%s' servers at ports %s", replicaSetName,
						StringUtils.arrayToCommaDelimitedString(serverPorts)));

				mongosTestFactory.start();

				LOGGER
						.info(String.format("Replica set '%s' started. Connection String: %s", replicaSetName, connectionString()));
			} catch (Throwable e) {
				throw new RuntimeException(" Error while starting cluster. ", e);
			}
		}

		private List<IMongodConfig> initReplicaSet() {
			List<IMongodConfig> replicaSet1 = new ArrayList<>();

			for (int port : serverPorts) {
				replicaSet1.add(defaultMongodConfig(serverVersion, port, defaultCommandOptions(), false, true, replicaSetName));
			}
			return replicaSet1;
		}

		private List<IMongodConfig> initConfigServers() {
			List<IMongodConfig> configServers = new ArrayList<>(configServerPorts.length);

			for (Integer port : configServerPorts) {
				configServers.add(
						defaultMongodConfig(serverVersion, port, defaultCommandOptions(), true, false, configServerReplicaSetName));
			}
			return configServers;
		}

		@Override
		public void stop() {

			if (mongosTestFactory != null) {

				LOGGER.info(String.format("Stopping replica set '%s' servers at ports %s", replicaSetName,
						StringUtils.arrayToCommaDelimitedString(serverPorts)));

				mongosTestFactory.stop();
			}
		}

		@Override
		public String connectionString() {
			return "mongodb://localhost:" + serverPorts[0] + "/?replicaSet=" + replicaSetName;
		}
	}

	private static IMongoCmdOptions defaultCommandOptions() {

		return new MongoCmdOptionsBuilder() //
				.useNoPrealloc(false) //
				.useSmallFiles(false) //
				.useNoJournal(false) //
				.useStorageEngine(STORAGE_ENGINE) //
				.verbose(false) //
				.build();
	}

	private static IMongodConfig defaultMongodConfig(IFeatureAwareVersion version, int port, IMongoCmdOptions cmdOptions,
			boolean configServer, boolean shardServer, String replicaSet) {

		try {
			MongodConfigBuilder builder = new MongodConfigBuilder() //
					.version(version) //
					.net(new Net(LOCALHOST, port, Network.localhostIsIPv6())) //
					.configServer(configServer).cmdOptions(cmdOptions); //

			if (StringUtils.hasText(replicaSet)) {

				builder = builder //
						.replication(new Storage(null, replicaSet, 0));

				if (!configServer) {
					builder = builder.shardServer(shardServer);
				} else {
					builder = builder.shardServer(false);
				}
			}

			return builder.build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static IMongosConfig defaultMongosConfig(IFeatureAwareVersion version, int port, IMongoCmdOptions cmdOptions,
			String configServerReplicaSet, int configServerPort) {

		try {
			MongosConfigBuilder builder = new MongosConfigBuilder() //
					.version(version) //
					.net(new Net(LOCALHOST, port, Network.localhostIsIPv6())) //
					.cmdOptions(cmdOptions);

			if (StringUtils.hasText(configServerReplicaSet)) {
				builder = builder.replicaSet(configServerReplicaSet) //
						.configDB(LOCALHOST + ":" + configServerPort);
			}

			return builder.build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
