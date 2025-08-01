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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.convert.DefaultJdbcTypeFactory;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.convert.MappingJdbcConverter;
import org.springframework.data.jdbc.core.convert.RelationResolver;
import org.springframework.data.jdbc.core.dialect.DialectResolver;
import org.springframework.data.jdbc.core.dialect.JdbcDialect;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.relational.core.mapping.DefaultNamingStrategy;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * Multiple DataSource Configuration
 *
 * Sets up <code>DataSource</code>, <codd>NamedParameterJdbcOperations</codd>, and <code>TransactionManager</code>
 * for each Spring Data JDBC Repository.
 *
 * The second configuration also sets up the required components for Spring Data JDBC without using explicit
 * AutoConfiguration.
 *
 * @author Daniel Frey
 */
@Configuration
public class DataSourceConfiguration {

    @Configuration
    static class Ds1Configuration {

        @Bean("ds1")
        DataSource dataSource1() {

            var builder = new EmbeddedDatabaseBuilder();

            return builder
                    .setType( EmbeddedDatabaseType.H2 )
                    .generateUniqueName( true )
                    .addScript( "classpath:/ds1-schema.sql" )
                    .addScript( "classpath:/ds1-data.sql" )
                    .build();
        }

        @Bean("jdbcOperations1" )
        NamedParameterJdbcOperations jdbcOperations1() {

            return new NamedParameterJdbcTemplate( dataSource1() );
        }

        @Bean( "transactionManager1" )
        PlatformTransactionManager transactionManager1() {

            return new DataSourceTransactionManager( dataSource1() );
        }

    }

    @Configuration
    @EnableAutoConfiguration(exclude = {
            DataSourceAutoConfiguration.class,
            JdbcRepositoriesAutoConfiguration.class
    })
    static class Ds2Configuration {

        @Bean( "ds2" )
        DataSource dataSource2() {

            var builder = new EmbeddedDatabaseBuilder();

            return builder
                    .setType( EmbeddedDatabaseType.H2 )
                    .generateUniqueName( true )
                    .addScript( "classpath:/ds2-schema.sql" )
                    .addScript( "classpath:/ds2-data.sql" )
                    .build();
        }

        @Bean( "jdbcOperations2" )
        NamedParameterJdbcOperations jdbcOperations1() {

            return new NamedParameterJdbcTemplate( dataSource2() );
        }

        @Bean( "transactionManager2" )
        PlatformTransactionManager transactionManager2() {

            return new DataSourceTransactionManager( dataSource2() );
        }

        @Bean
        JdbcDialect jdbcDialect2( @Qualifier( "jdbcOperations2" ) NamedParameterJdbcOperations jdbcOperations ) {

            return DialectResolver.getDialect( jdbcOperations.getJdbcOperations() );
        }

        @Bean
        JdbcCustomConversions customConversions2() {

            return new JdbcCustomConversions();
        }

        @Bean
        JdbcMappingContext jdbcMappingContext2( Optional<NamingStrategy> namingStrategy, JdbcCustomConversions customConversions2 ) {

            var mappingContext = new JdbcMappingContext( namingStrategy.orElse( DefaultNamingStrategy.INSTANCE ) );
            mappingContext.setSimpleTypeHolder( customConversions2.getSimpleTypeHolder() );

            return mappingContext;
        }

        @Bean
        JdbcConverter jdbcConverter2(
                JdbcMappingContext jdbcMappingContext2,
                @Qualifier( "jdbcOperations2" ) NamedParameterJdbcOperations jdbcOperations2,
                @Lazy RelationResolver relationResolver,
                JdbcCustomConversions customConversions2
        ) {

            var jdbcTypeFactory = new DefaultJdbcTypeFactory( jdbcOperations2.getJdbcOperations() );

            return new MappingJdbcConverter( jdbcMappingContext2, relationResolver, customConversions2, jdbcTypeFactory );
        }

    }

}
