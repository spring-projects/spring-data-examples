/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.jdbc.howto.multipledatasources.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

/**
 * Configures the Spring Data JDBC Repositories with explicit callouts to `jdbcOperationsRef`
 * and `transactionManagerRef`
 *
 * @see <code>DataSourceConfiguration.java</code>
 *
 * @author Daniel Frey
 */
@Configuration
public class JdbcConfiguration {

    @Configuration
    @EnableJdbcRepositories(
            basePackages = { "example.springdata.jdbc.howto.multipledatasources.minion" },
            jdbcOperationsRef = "jdbcOperations1",
            transactionManagerRef = "transactionManager1"
    )
    static class MinionJdbcConfiguration {

    }

    @Configuration
    @EnableJdbcRepositories(
            basePackages = { "example.springdata.jdbc.howto.multipledatasources.person" },
            jdbcOperationsRef = "jdbcOperations2",
            transactionManagerRef = "transactionManager2"
    )
    static class PersonJdbcConfiguration {

    }

}
