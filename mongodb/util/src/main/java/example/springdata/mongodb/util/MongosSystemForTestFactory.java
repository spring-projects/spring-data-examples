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
package example.springdata.mongodb.util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.Map.Entry;

import com.mongodb.*;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.MongosExecutable;
import de.flapdoodle.embed.mongo.MongosProcess;
import de.flapdoodle.embed.mongo.MongosStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.IMongosConfig;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;

class MongosSystemForTestFactory {

	private final static Logger logger = LoggerFactory
			.getLogger(MongosSystemForTestFactory.class);

	public static final String ADMIN_DATABASE_NAME = "admin";
	public static final String LOCAL_DATABASE_NAME = "local";
	public static final String REPLICA_SET_NAME = "rep1";
	public static final String OPLOG_COLLECTION = "oplog.rs";

	private final IMongosConfig config;
	private final Map<String, List<IMongodConfig>> replicaSets;
	private final List<IMongodConfig> configServers;
	private final String shardDatabase;
	private final String shardCollection;
	private final String shardKey;
	private final Function<Command, ProcessOutput> outputFunction;

	private MongosExecutable mongosExecutable;
	private MongosProcess mongosProcess;
	private List<MongodProcess> mongodProcessList;
	private List<MongodProcess> mongodConfigProcessList;

	public MongosSystemForTestFactory(IMongosConfig config,
									  Map<String, List<IMongodConfig>> replicaSets,
									  List<IMongodConfig> configServers, String shardDatabase,
									  String shardCollection, String shardKey, Function<Command, ProcessOutput> outputFunction) {
		this.config = config;
		this.replicaSets = replicaSets;
		this.configServers = configServers;
		this.shardDatabase = shardDatabase;
		this.shardCollection = shardCollection;
		this.shardKey = shardKey;
		this.outputFunction = outputFunction;
	}

	public void start() throws Throwable {
		this.mongodProcessList = new ArrayList<>();
		this.mongodConfigProcessList = new ArrayList<>();
		for (Entry<String, List<IMongodConfig>> entry : replicaSets.entrySet()) {
			initializeReplicaSet(entry);
		}
		for (IMongodConfig config : configServers) {
			initializeConfigServer(config);
		}
		initializeMongos();
		configureMongos();
	}

	private void initializeReplicaSet(Entry<String, List<IMongodConfig>> entry)
			throws Exception {
		String replicaName = entry.getKey();
		List<IMongodConfig> mongoConfigList = entry.getValue();

		if (mongoConfigList.size() < 3) {
			throw new Exception(
					"A replica set must contain at least 3 members.");
		}
		// Create 3 mongod processes
		for (IMongodConfig mongoConfig : mongoConfigList) {
			if (!mongoConfig.replication().getReplSetName().equals(replicaName)) {
				throw new Exception(
						"Replica set name must match in mongo configuration");
			}
			IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
				.defaultsWithLogger(Command.MongoD,logger)
				.processOutput(outputFunction.apply(Command.MongoD))
				.build();
			MongodStarter starter = MongodStarter.getInstance(runtimeConfig);
			MongodExecutable mongodExe = starter.prepare(mongoConfig);
			MongodProcess process = mongodExe.start();
			mongodProcessList.add(process);
		}
		Thread.sleep(1000);
		MongoClientOptions mo = MongoClientOptions.builder()
				.connectTimeout(10)
				.build();
		MongoClient mongo = new MongoClient(new ServerAddress(mongoConfigList.get(0).net()
				.getServerAddress().getHostName(), mongoConfigList.get(0).net()
				.getPort()), mo);
		DB mongoAdminDB = mongo.getDB(ADMIN_DATABASE_NAME);

		CommandResult cr = mongoAdminDB
				.command(new BasicDBObject("isMaster", 1));
		logger.info("isMaster: {}", cr);

		// Build BSON object replica set settings
		DBObject replicaSetSetting = new BasicDBObject();
		replicaSetSetting.put("_id", replicaName);
		BasicDBList members = new BasicDBList();
		int i = 0;
		for (IMongodConfig mongoConfig : mongoConfigList) {
			DBObject host = new BasicDBObject();
			host.put("_id", i++);
			host.put("host", mongoConfig.net().getServerAddress().getHostName()
					+ ":" + mongoConfig.net().getPort());
			members.add(host);
		}

		replicaSetSetting.put("members", members);
		logger.info(replicaSetSetting.toString());
		// Initialize replica set
		cr = mongoAdminDB.command(new BasicDBObject("replSetInitiate",
				replicaSetSetting));
		logger.info("replSetInitiate: {}", cr);

		Thread.sleep(5000);
		cr = mongoAdminDB.command(new BasicDBObject("replSetGetStatus", 1));
		logger.info("replSetGetStatus: {}", cr);

		// Check replica set status before to proceed
		while (!isReplicaSetStarted(cr)) {
			logger.info("Waiting for 3 seconds...");
			Thread.sleep(1000);
			cr = mongoAdminDB.command(new BasicDBObject("replSetGetStatus", 1));
			logger.info("replSetGetStatus: {}", cr);
		}

		mongo.close();
		mongo = null;
	}

	private boolean isReplicaSetStarted(BasicDBObject setting) {
		if (setting.get("members") == null) {
			return false;
		}

		BasicDBList members = (BasicDBList) setting.get("members");
		for (Object m : members.toArray()) {
			BasicDBObject member = (BasicDBObject) m;
			logger.info(member.toString());
			int state = member.getInt("state");
			logger.info("state: {}", state);
			// 1 - PRIMARY, 2 - SECONDARY, 7 - ARBITER
			if (state != 1 && state != 2 && state != 7) {
				return false;
			}
		}
		return true;
	}

	private void initializeConfigServer(IMongodConfig config) throws Exception {
		if (!config.isConfigServer()) {
			throw new Exception(
					"Mongo configuration is not a defined for a config server.");
		}
		MongodStarter starter = MongodStarter.getDefaultInstance();
		MongodExecutable mongodExe = starter.prepare(config);
		MongodProcess process = mongodExe.start();
		mongodProcessList.add(process);
	}

	private void initializeMongos() throws Exception {
		MongosStarter runtime = MongosStarter.getInstance(new RuntimeConfigBuilder()
			.defaultsWithLogger(Command.MongoS,logger)
			.processOutput(outputFunction.apply(Command.MongoS))
			.build());

		mongosExecutable = runtime.prepare(config);
		mongosProcess = mongosExecutable.start();
	}

	private void configureMongos() throws Exception {
		CommandResult cr;
		MongoClientOptions options = MongoClientOptions.builder()
				.connectTimeout(10)
				.build();
		try (MongoClient mongo = new MongoClient(
				new ServerAddress(this.config.net().getServerAddress()
						.getHostName(), this.config.net().getPort()), options)) {
			DB mongoAdminDB = mongo.getDB(ADMIN_DATABASE_NAME);

			// Add shard from the replica set list
			for (Entry<String, List<IMongodConfig>> entry : this.replicaSets
					.entrySet()) {
				String replicaName = entry.getKey();
				String command = "";
				for (IMongodConfig mongodConfig : entry.getValue()) {
					if (command.isEmpty()) {
						command = replicaName + "/";
					} else {
						command += ",";
					}
					command += mongodConfig.net().getServerAddress().getHostName()
							+ ":" + mongodConfig.net().getPort();
				}
				logger.info("Execute add shard command: {}", command);
				cr = mongoAdminDB.command(new BasicDBObject("addShard", command));
				logger.info(cr.toString());
			}

			logger.info("Execute list shards.");
			cr = mongoAdminDB.command(new BasicDBObject("listShards", 1));
			logger.info(cr.toString());

			// Enabled sharding at database level
			logger.info("Enabled sharding at database level");
			cr = mongoAdminDB.command(new BasicDBObject("enableSharding",
					this.shardDatabase));
			logger.info(cr.toString());

			// Create index in sharded collection
			logger.info("Create index in sharded collection");
			DB db = mongo.getDB(this.shardDatabase);
			db.getCollection(this.shardCollection).createIndex(this.shardKey);

			// Shard the collection
			logger.info("Shard the collection: {}.{}", this.shardDatabase, this.shardCollection);
			DBObject cmd = new BasicDBObject();
			cmd.put("shardCollection", this.shardDatabase + "." + this.shardCollection);
			cmd.put("key", new BasicDBObject(this.shardKey, 1));
			cr = mongoAdminDB.command(cmd);
			logger.info(cr.toString());

			logger.info("Get info from config/shards");
			DBCursor cursor = mongo.getDB("config").getCollection("shards").find();
			while (cursor.hasNext()) {
				DBObject item = cursor.next();
				logger.info(item.toString());
			}
		}

	}

	public Mongo getMongo() throws UnknownHostException, MongoException {
		return new MongoClient(new ServerAddress(mongosProcess.getConfig().net()
				.getServerAddress(), mongosProcess.getConfig().net().getPort()));
	}

	public void stop() {
		for (MongodProcess process : this.mongodProcessList) {
			process.stop();
		}
		for (MongodProcess process : this.mongodConfigProcessList) {
			process.stop();
		}
		this.mongosProcess.stop();
	}
}
