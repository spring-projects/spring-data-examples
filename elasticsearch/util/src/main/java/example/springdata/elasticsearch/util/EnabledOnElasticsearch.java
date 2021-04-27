/*
 * Copyright 2021-2021 the original author or authors.
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
package example.springdata.elasticsearch.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * {@code @EnableOnElasticsearch} is used to signal that the annotated test class or test method is only
 * <em>enabled</em> if Elasticsearch is available at {@link #url()}.
 * <p>
 * When applied at the class level, all test methods within that class will be enabled if Elasticsearch is available.
 * <p>
 * If a test method is disabled via this annotation, that does not prevent the test class from being instantiated.
 * Rather, it prevents the execution of the test method and method-level lifecycle callbacks such as {@code @BeforeEach}
 * methods, {@code @AfterEach} methods, and corresponding extension APIs.
 *
 * @author Prakhar Gupta
 * @author Mark Paluch
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ExtendWith(ElasticsearchAvailableCondition.class)
public @interface EnabledOnElasticsearch {

	/**
	 * URL pointing at the Elasticsearch instance to check.
	 */
	String url() default "http://localhost:9200";

}
