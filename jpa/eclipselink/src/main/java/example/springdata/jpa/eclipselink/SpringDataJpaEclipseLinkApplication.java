/*
 * Copyright 2015 the original author or authors.
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

package example.springdata.jpa.eclipselink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring Boot application with Spring Java Config annotations that use EclipseLink as the JPA Provider
 * and turn on Static Weaving of the entity classes by disabling dynamic weaving and utilizing a maven
 * build that includes a static weaving plugin.
 *
 * @author Jeremy Rickard
 */

@SpringBootApplication
@Configuration
@EnableJpaRepositories("example.springdata.jpa.eclipselink.repositories")
public class SpringDataJpaEclipseLinkApplication {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
        lemfb.setDataSource(dataSource());
        lemfb.setJpaVendorAdapter(jpaVendorAdapter());
        lemfb.setJpaDialect(eclipseLinkJpaDialect());
        lemfb.setJpaPropertyMap(jpaProperties());
        lemfb.setPackagesToScan("example.springdata.jpa.eclipselink.domain");
        return lemfb;
    }

    @Bean
    public EclipseLinkJpaDialect eclipseLinkJpaDialect() {
        return new EclipseLinkJpaDialect();
    }

    /*
     * Set this property to disable LoadTimeWeaver (i.e. Dynamic Weaving) for EclipseLink.
     * Otherwise, you'll get: Cannot apply class transformer without LoadTimeWeaver specified
     */
    @Bean
    public Map<String, String> jpaProperties() {
        Map<String, String> props = new HashMap<>();
        props.put("eclipselink.weaving", "false");
        return props;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter jpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.HSQL);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaEclipseLinkApplication.class, args);
    }
}
