/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.geode.client.security.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;

/**
 * @author Patrick Johnson
 */
@Configuration
@EnableSecurity(securityManagerClassName = "example.springdata.geode.client.security.server.SecurityManagerProxy")
@Profile({ "default", "geode-security-manager-proxy-configuration" })
public class GeodeIntegratedSecurityProxyConfiguration {}
