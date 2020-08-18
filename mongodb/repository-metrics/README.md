# Spring Data MongoDB 3.1 - Repository Metrics

Configure a `RepositoryMethodInvocationListener` to capture invocation metrics on `Repository` interfaces.

```java
@Configuration(proxyBeanMethods = false)
class RepositoryMetricsConfiguration {

	@Bean
	public RepoMetricsPostProcessor repoMetricsPostProcessor() {
		return new RepoMetricsPostProcessor();
	}

	static class RepoMetricsPostProcessor implements BeanPostProcessor {

		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

			if (bean instanceof RepositoryFactoryBeanSupport) {

				RepositoryFactoryBeanSupport<?, ?, ?> repositoryFactoryBean = (RepositoryFactoryBeanSupport<?, ?, ?>) bean;

				repositoryFactoryBean.addRepositoryFactoryCustomizer(repositoryFactory -> {
					repositoryFactory.addInvocationListener(System.out::println);  // register the invocation listener
				});
			}

			return bean;
		}
	}
}
```
