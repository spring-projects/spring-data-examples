package example.springdata.rest.entitylookup;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * @author Steve Rutherford
 */
@Configuration
public class SpringDataRestCustomization implements RepositoryRestConfigurer {
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.withEntityLookup()
				.forRepository(UserRepo.class, User::getUsername, UserRepo::findByUsername);
	}
}

