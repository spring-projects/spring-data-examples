/*
 * Copyright 2020 the original author or authors.
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
package example.springdata.cassandra.spel;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import reactor.core.publisher.Mono;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.data.spel.spi.ReactiveEvaluationContextExtension;

/**
 * Simple configuration for reactive Cassandra SpEL support.
 *
 * @author Mark Paluch
 */
@SpringBootApplication
class ApplicationConfiguration {

	@Bean
	ReactiveTenantExtension tenantExtension() {
		return ReactiveTenantExtension.INSTANCE;
	}

	/**
	 * Extension that looks up a {@link Tenant} from the {@link reactor.util.context.Context}.
	 */
	enum ReactiveTenantExtension implements ReactiveEvaluationContextExtension {

		INSTANCE;

		@Override
		public Mono<? extends EvaluationContextExtension> getExtension() {
			return Mono.deferContextual(contextView -> Mono.just(new TenantExtension(contextView.get(Tenant.class))));
		}

		@Override
		public String getExtensionId() {
			return "my-reactive-tenant-extension";
		}
	}

	/**
	 * Actual extension providing access to the {@link Tenant} value object.
	 */
	@RequiredArgsConstructor
	static class TenantExtension implements EvaluationContextExtension {

		private final Tenant tenant;

		@Override
		public String getExtensionId() {
			return "my-tenant-extension";
		}

		@Override
		public Tenant getRootObject() {
			return tenant;
		}
	}

	/**
	 * The root object.
	 */
	@Value
	public static class Tenant {

		String tenantId;

	}
}
