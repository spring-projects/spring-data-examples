package example.springdata.jdbc.howto.multipledatasources.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

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
