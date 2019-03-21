/*
 * Copyright 2018 the original author or authors.
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
package example.springdata.test.util;

import static java.lang.Boolean.*;

import lombok.Value;

import java.util.function.Supplier;

import org.junit.AssumptionViolatedException;
import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.util.Lazy;
import org.springframework.util.Assert;

/**
 * A JUnit Rule for handling the discovery or creation of required infrastructure as for example databases. It supports
 * multiple detection/creation strategies, supplied via {@link Supplier}. The system property
 * {@code ignoreMissingInfrastructure} controls if tests get ignored when infrastructure is missing, or if the first
 * test will throw an exception.
 *
 * This rule is intended to be instantiate by a Spring {@link org.springframework.context.ApplicationContext} and injected into tests.
 * This enables shutdown of the infrastructure when the {@link org.springframework.context.ApplicationContext} gets shutdown.
 *
 * @author Jens Schauder
 */
public class InfrastructureRule<T> extends ExternalResource implements DisposableBean {

	private static final String IGNORE_MISSING_INFRASTRUCTURE_PROPERTY_NAME = "ignoreMissingInfrastructure";
	private final boolean ignoreMissingInfrastructure;
	private final Supplier<InfrastructureInfo<T>> cachedResourceInfo;
	private boolean isSubsequentCall = false;

	/**
	 * Creates or find the infrastructure using the provided {@link Supplier}s. The suppliers get tried until the first one returns a valid {@link InfrastructureInfo}.
	 *
	 * @param possibleSources different strategies to acquire the required infrastructure.
	 */
	public InfrastructureRule(Supplier<InfrastructureInfo<T>>... possibleSources) {

		this(getIgnoreMissingInfrastructureProperty(), possibleSources);
	}

	private static boolean getIgnoreMissingInfrastructureProperty() {
		return Boolean.parseBoolean(System.getProperty(IGNORE_MISSING_INFRASTRUCTURE_PROPERTY_NAME, TRUE.toString()));
	}

	InfrastructureRule(boolean ignoreMissingInfrastructure, Supplier<InfrastructureInfo<T>>... possibleSources) {

		this(ignoreMissingInfrastructure, () -> buildInfoForFirstWorkingSource(possibleSources));

		Assert.notEmpty(possibleSources, "We need at least one possibleSource for a resource.");
	}

	private InfrastructureRule(boolean ignoreMissingInfrastructure,
			Supplier<InfrastructureInfo<T>> resourceInfoSupplier) {

		this.ignoreMissingInfrastructure = ignoreMissingInfrastructure;
		this.cachedResourceInfo = Lazy.of(resourceInfoSupplier);
	}

	@Override
	protected void before() throws Throwable {

		if (!cachedResourceInfo.get().isValid()) {
			String message = "Required infrastructure not available available";

			if (ignoreMissingInfrastructure || isSubsequentCall) {
				throw new AssumptionViolatedException(message);
			} else {
				isSubsequentCall = true;
				throw new IllegalStateException(message);
			}
		}
	}

	public T getInfo() {
		return cachedResourceInfo.get().getInfo();
	}

	@Override
	public void destroy() throws Exception {
		cachedResourceInfo.get().destroy.run();
	}

	static private <T> InfrastructureInfo<T> buildInfoForFirstWorkingSource(
			Supplier<InfrastructureInfo<T>>... possibleSources) {

		Throwable lastException = null;
		for (Supplier<InfrastructureInfo<T>> source : possibleSources) {

			InfrastructureInfo<T> info = source.get();
			if (info.isValid()) {
				return info;
			}

			lastException = info.getException();
		}

		return new InfrastructureInfo<>(false, null, lastException, () -> {});
	}

	/**
	 * Information about an attempt to find or create required infrastructure.
	 *
	 * @param <T> the type of information offered to the tests about the infrastructure.
	 */
	@Value
	public static class InfrastructureInfo<T> {

		/** if the infrastructure was found or not */
		boolean valid;

		/** information about the infrastructure, e.g. an URL where to reach it. */
		T info;

		/** Any throwable thrown when failing to create or find the required infrastructure. */
		Throwable exception;

		/** A runnable to execute to shutdown the infrastructure. */
		Runnable destroy;
	}
}
