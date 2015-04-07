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
package example.springdata.jpa.customall;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Sample configuration to bootstrap Spring Data JPA through JavaConfig. Note how Spring Data JPA is configured with a
 * custom repository base class. This causes all repository interfaces being found for this configuration to use the
 * configured class as base repository.
 * 
 * @author Oliver Gierke
 * @soundtrack Tim Neuhaus - As life found you (The Cabinet)
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(repositoryBaseClass = ExtendedJpaRepository.class)
class CustomRepositoryConfig {}
