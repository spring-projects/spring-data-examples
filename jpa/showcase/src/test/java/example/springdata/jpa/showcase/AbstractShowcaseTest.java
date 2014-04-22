/*
 * Copyright 2011-2014 the original author or authors.
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
package example.springdata.jpa.showcase;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import example.springdata.jpa.showcase.AbstractShowcaseTest.TestConfig;

/**
 * @author Oliver Gierke
 */
@SpringApplicationConfiguration(classes = TestConfig.class)
@Transactional
public abstract class AbstractShowcaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Configuration
	@EnableAutoConfiguration
	@ComponentScan
	static class TestConfig {

	}

	@BeforeTransaction
	public void setupData() throws Exception {

		deleteFromTables("account", "customer");
		executeSqlScript("classpath:data.sql", false);
	}
}
