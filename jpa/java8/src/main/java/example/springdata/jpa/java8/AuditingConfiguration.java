/*
 * Copyright 2014-2018 the original author or authors.
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
package example.springdata.jpa.java8;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;

@EnableAsync
@SpringBootApplication
@EnableJpaAuditing
class AuditingConfiguration {

	@Bean
	public static BeanFactoryPostProcessor foo() {

		return new BeanFactoryPostProcessor() {

			@Override
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

				Arrays.stream(beanFactory.getBeanNamesForType(AbstractEntityManagerFactoryBean.class, false, false))
						.peek(it -> System.out.println(it)) //
						.forEach(it -> beanFactory.getBeanDefinition(it.substring(1)).setLazyInit(true));

				Arrays.stream(beanFactory.getBeanNamesForType(PlatformTransactionManager.class, false, false)) //
						.peek(it -> System.out.println(it)) //
						.forEach(it -> beanFactory.getBeanDefinition(it).setLazyInit(true));

				Arrays.stream(beanFactory.getBeanNamesForType(JpaContext.class, false, false)) //
						.peek(it -> System.out.println(it)) //
						.forEach(it -> beanFactory.getBeanDefinition(it).setLazyInit(true));
			}
		};
	}
}
