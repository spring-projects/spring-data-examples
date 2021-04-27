/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.geode.server.expiration.eviction;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.config.annotation.EnableExpiration;
import org.springframework.data.gemfire.expiration.ExpirationActionType;

/**
 * @author Patrick Johnson
 */
@Profile("!default")
@Configuration
@EnableExpiration(policies = {
		@EnableExpiration.ExpirationPolicy(timeout = 2, action = ExpirationActionType.DESTROY,
				regionNames = { "Customers" }, types = { EnableExpiration.ExpirationType.TIME_TO_LIVE }),
		@EnableExpiration.ExpirationPolicy(timeout = 1, action = ExpirationActionType.DESTROY,
				regionNames = { "Customers" }, types = { EnableExpiration.ExpirationType.IDLE_TIMEOUT }) })
public class ExpirationPolicyConfig {}
