/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 *  * agreements. See the NOTICE file distributed with this work for additional information regarding
 *  * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance with the License. You may obtain a
 *  * copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License
 *  * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  * or implied. See the License for the specific language governing permissions and limitations under
 *  * the License.
 *
 */

package example.springdata.geode.functions.cascading.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.config.annotation.EnableManager;

@SpringBootApplication
@EnableManager(start = true)
@ComponentScan(basePackages = "example.springdata.geode.functions.cascading.server.functions")
@Import(CascadingFunctionServerConfig.class)
public class CascadingFunctionServer {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplicationBuilder(CascadingFunctionServer.class)
				.web(WebApplicationType.NONE)
				.build();

		String profile = "default";
		if (args.length != 0) {
			profile = args[0];
		}

		springApplication.setAdditionalProfiles(profile);
		springApplication.run(args);
	}
}