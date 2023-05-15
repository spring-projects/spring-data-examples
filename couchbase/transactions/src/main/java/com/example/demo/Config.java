/*
 * Copyright 2022 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.couchbase.client.core.msg.kv.DurabilityLevel;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.transactions.config.TransactionsConfig;

/**
 * @author Michael Reiche
 */
@Configuration
@EnableCouchbaseRepositories({ "com.example.demo" })
@EnableTransactionManagement
public class Config extends AbstractCouchbaseConfiguration {
	@Override
	public String getConnectionString() {
		return "127.0.0.1";
	}

	@Override
	public String getUserName() {
		return "Administrator";
	}

	@Override
	public String getPassword() {
		return "password";
	}

	@Override
	public String getBucketName() {
		return "travel-sample";
	}

	@Override
	public void configureEnvironment(ClusterEnvironment.Builder builder) {
		builder.transactionsConfig(TransactionsConfig.durabilityLevel(DurabilityLevel.NONE));
	}

}
