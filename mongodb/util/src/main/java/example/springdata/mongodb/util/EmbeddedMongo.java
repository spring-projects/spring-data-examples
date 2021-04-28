/*
 * Copyright 2018-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.mongodb.util;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.ImmutableMongosConfig;
import de.flapdoodle.embed.mongo.config.MongoCmdOptions;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.MongosConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Feature;
import de.flapdoodle.embed.mongo.distribution.IFeatureAwareVersion;
import de.flapdoodle.embed.mongo.distribution.Versions;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.embed.process.io.Processors;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.junit.AssumptionViolatedException;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * {@link org.junit.rules.TestRule} for a MongoDB server resource that is started/stopped along the test lifecycle.
 *
 * @author Christoph Strobl
 * @author Mark Paluch
 */
public class EmbeddedMongo extends ExternalResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedMongo.class);

	private static final String LOCALHOST = "127.0.0.1";
	private static final String DEFAULT_REPLICA_SET_NAME = "rs0";
	private static final String DEFAULT_CONFIG_SERVER_REPLICA_SET_NAME = "rs-config";

	private static final String STORAGE_ENGINE = "wiredTiger";

	private static final IFeatureAwareVersion VERSION = Versions.withFeatures(Version.of("3.7.9"),
			Feature.ONLY_WITH_SSL, Feature.ONLY_64BIT, Feature.NO_HTTP_INTERFACE_ARG, Feature.STORAGE_ENGINE,
			Feature.MONGOS_CONFIGDB_SET_STYLE, Feature.NO_CHUNKSIZE_ARG);

	private final TestResource resource;

	private EmbeddedMongo(TestResource resource) {
		this.resource = resource;
	}

	/**
	 * Create a new {@link Builder} to build {@link EmbeddedMongo}.
	 *
	 * @return
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Create a new {@link Builder} that is initialized as replica set to build {@link EmbeddedMongo}.
	 *
	 * @return
	 */
	public static Builder replSet() {
		return replSet(DEFAULT_REPLICA_SET_NAME);
	}

	public static Builder replSet(String replicaSetName) {
		return new Builder().withReplicaSetName(replicaSetName);
	}

	/**
	 * {@link Builder} for {@link EmbeddedMongo}.
	 */
	public static class Builder {

		IFeatureAwareVersion version;
		String replicaSetName;
		List<Integer> serverPorts;
		List<Integer> configServerPorts;
		boolean silent = true;

		Builder() {

			version = VERSION;
			replicaSetName = null;
			serverPorts = Collections.emptyList();
			configServerPorts = Collections.emptyList();
		}

		/**
		 * Configure the MongoDB {@link IFeatureAwareVersion version}.
		 *
		 * @param version
		 * @return
		 */
		public Builder withVersion(IFeatureAwareVersion version) {

			this.version = version;
			return this;
		}

		/**
		 * Configure the replica set name.
		 *
		 * @param version
		 * @return
		 */
		public Builder withReplicaSetName(String replicaSetName) {

			this.replicaSetName = replicaSetName;
			return this;
		}

		/**
		 * Configure the server ports.
		 *
		 * @param version
		 * @return
		 */
		public Builder withServerPorts(Integer... ports) {

			this.serverPorts = Arrays.asList(ports);
			return this;
		}

		/**
		 * Configure whether to stay silent (stream only Mongo process errors to stdout) or to stream all process output to
		 * stdout. By default, only process errors are forwarded to stdout.
		 *
		 * @param silent
		 * @return
		 */
		public Builder withSilent(boolean silent) {

			this.silent = silent;
			return this;
		}

		public EmbeddedMongo configure() {

			if (serverPorts.size() > 1 || StringUtils.hasText(replicaSetName)) {

				var rsName = StringUtils.hasText(replicaSetName) ? replicaSetName : DEFAULT_REPLICA_SET_NAME;
				return new EmbeddedMongo(
						new ReplSet(version, rsName, silent, serverPorts.toArray(new Integer[serverPorts.size()])));
			}

			throw new UnsupportedOperationException("implement me");
		}

	}

	@Override
	protected void before() {

		try {
			resource.start();
		} catch (RuntimeException e) {
			LOGGER.error("Cannot start MongoDB", e);
			throw new AssumptionViolatedException("Cannot start MongoDB. Skipping", e);
		}
	}

	@Override
	protected void after() {

		try {
			resource.stop();
		} catch (RuntimeException e) {
			LOGGER.error("Cannot stop MongoDB", e);
		}
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

	/**
	 * Interface specifying a test resource which exposes lifecycle methods and connection coordinates.
	 */
	interface TestResource {

		/**
		 * Start the resource.
		 */
		void start();

		/**
		 * Stop the resource.
		 */
		void stop();

		/**
		 * @return the connection string to configure a MongoDB client.
		 */
		String connectionString();

		default MongoClient mongoClient() {
			return MongoClients.create(connectionString());
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
		private final Function<Command, ProcessOutput> outputFunction;

		private MongosSystemForTestFactory mongosTestFactory;

		ReplSet(IFeatureAwareVersion serverVersion, String replicaSetName, boolean silent, Integer... serverPorts) {

			this.serverVersion = serverVersion;
			this.replicaSetName = replicaSetName;
			this.serverPorts = defaultPortsIfRequired(serverPorts);
			this.configServerPorts = defaultPortsIfRequired(null);
			this.configServerReplicaSetName = DEFAULT_CONFIG_SERVER_REPLICA_SET_NAME;
			this.mongosPort = randomOrDefaultServerPort();

			if (silent) {
				outputFunction = it -> new ProcessOutput(Processors.silent(),
						Processors.namedConsole("[ " + it.commandName() + " error]"), Processors.console());
			} else {
				outputFunction = it -> ProcessOutput.getDefaultInstance(it.commandName());
			}
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

			Map<String, List<MongodConfig>> replicaSets = new LinkedHashMap<>();
			replicaSets.put(configServerReplicaSetName, initConfigServers());
			replicaSets.put(replicaSetName, initReplicaSet());

			// create mongos
			var mongosConfig = defaultMongosConfig(serverVersion, mongosPort, defaultCommandOptions(),
					configServerReplicaSetName, configServerPorts[0]);

			mongosTestFactory = new MongosSystemForTestFactory(mongosConfig, replicaSets, Collections.emptyList(),
					DEFAULT_SHARDING, DEFAULT_SHARDING, DEFAULT_SHARD_KEY, outputFunction);

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

		private List<MongodConfig> initReplicaSet() {

			List<MongodConfig> rs = new ArrayList<>();

			for (int port : serverPorts) {
				rs.add(defaultMongodConfig(serverVersion, port, defaultCommandOptions(), false, true, replicaSetName));
			}
			return rs;
		}

		private List<MongodConfig> initConfigServers() {

			List<MongodConfig> configServers = new ArrayList<>(configServerPorts.length);

			for (var port : configServerPorts) {
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

	/**
	 * @return Default {@link IMongoCmdOptions command options}.
	 */
	private static MongoCmdOptions defaultCommandOptions() {

		return MongoCmdOptions.builder() //
				.useNoPrealloc(false) //
				.useSmallFiles(false) //
				.useNoJournal(false) //
				.storageEngine(STORAGE_ENGINE) //
				.isVerbose(false) //
				.build();
	}

	/**
	 * Create a default {@code mongod} config.
	 *
	 * @param version
	 * @param port
	 * @param cmdOptions
	 * @param configServer
	 * @param shardServer
	 * @param replicaSet
	 * @return
	 */
	private static MongodConfig defaultMongodConfig(IFeatureAwareVersion version, int port, MongoCmdOptions cmdOptions,
			boolean configServer, boolean shardServer, String replicaSet) {

		try {

			var builder = MongodConfig.builder() //
					.version(version) //
					.putArgs("--quiet", null) //
					.net(new Net(LOCALHOST, port, Network.localhostIsIPv6())) //
					.isConfigServer(configServer).cmdOptions(cmdOptions); //

			if (StringUtils.hasText(replicaSet)) {

				builder = builder //
						.replication(new Storage(null, replicaSet, 0));

				if (!configServer) {
					builder = builder.isShardServer(shardServer);
				} else {
					builder = builder.isShardServer(false);
				}
			}

			return builder.build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create a default {@code mongos} config.
	 *
	 * @param version
	 * @param port
	 * @param cmdOptions
	 * @param configServerReplicaSet
	 * @param configServerPort
	 * @return
	 */
	private static MongosConfig defaultMongosConfig(IFeatureAwareVersion version, int port, MongoCmdOptions cmdOptions,
			String configServerReplicaSet, int configServerPort) {

		try {

			var builder = MongosConfig.builder() //
					.version(version) //
					.putArgs("--quiet", null) //
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
