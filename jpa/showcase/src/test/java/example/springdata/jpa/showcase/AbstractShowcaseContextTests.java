/**
 * 
 */
package example.springdata.jpa.showcase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.event.EventPublishingTestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

/**
 *
 */
@ExtendWith(SpringExtension.class)
@TestExecutionListeners({ ServletTestExecutionListener.class, DirtiesContextBeforeModesTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		EventPublishingTestExecutionListener.class })
public abstract class AbstractShowcaseContextTests implements ApplicationContextAware {

	/**
	 * Logger available to subclasses.
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * The {@link ApplicationContext} that was injected into this test instance via
	 * {@link #setApplicationContext(ApplicationContext)}.
	 */
	@Nullable protected ApplicationContext applicationContext;

	/**
	 * Set the {@link ApplicationContext} to be used by this test instance, provided via {@link ApplicationContextAware}
	 * semantics.
	 * 
	 * @param applicationContext the ApplicationContext that this test runs in
	 */
	@Override
	public final void setApplicationContext(final ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
