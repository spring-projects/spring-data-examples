package example.springdata.test.util;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.AssumptionViolatedException;
import org.junit.Test;
import org.junit.runners.model.Statement;

/**
 * Unit tests for the {@link InfrastructureRule}.
 *
 * @author Jens Schauder
 */
public class InfrastructureRuleUnitTests {

	static final Statement EMPTY_STATEMENT = new Statement() {
		@Override
		public void evaluate() throws Throwable {

		}
	};

	@Test
	public void failingRuleIgnoresTestByDefault() {

		InfrastructureRule<Object> rule = new InfrastructureRule<>(true,
				() -> new InfrastructureRule.InfrastructureInfo<>(false, null, null, () -> {}));

		Statement statement = rule.apply(EMPTY_STATEMENT, null);

		assertThatExceptionOfType(AssumptionViolatedException.class).isThrownBy(statement::evaluate);
	}

	@Test
	public void succeedingRuleExecutesTest() throws Throwable {

		InfrastructureRule<Object> rule = new InfrastructureRule<>(true,
				() -> new InfrastructureRule.InfrastructureInfo<>(true, null, null, () -> {}));

		Statement originalStatement = mock(Statement.class);

		rule.apply(originalStatement, null).evaluate();

		verify(originalStatement, times(1)).evaluate();
	}

	@Test
	public void ifRequiredTheFirstTestWithMissingInfrastructureFails() {

		InfrastructureRule<Object> rule = new InfrastructureRule<>(false,
				() -> new InfrastructureRule.InfrastructureInfo<>(false, null, null, () -> {}));

		Statement statement = rule.apply(EMPTY_STATEMENT, null);

		assertThatIllegalStateException().isThrownBy(statement::evaluate);
		assertThatExceptionOfType(AssumptionViolatedException.class).isThrownBy(statement::evaluate);

	}

	@Test
	public void infrastructureRuleRequiresAtLeastOneSupplier() {

		assertThatIllegalArgumentException().isThrownBy(InfrastructureRule::new);
	}

}
