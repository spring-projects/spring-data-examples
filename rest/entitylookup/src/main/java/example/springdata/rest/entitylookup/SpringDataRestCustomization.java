package example.springdata.rest.associations;

// Source - https://stackoverflow.com/q/54651741
// Posted by undef, modified by community. See post 'Timeline' for change history
// Retrieved 2026-09-02, License - CC BY-SA 4.0

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class SpringDataRestCustomization implements RepositoryRestConfigurer {
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.withEntityLookup()
				.forRepository(UserRepo.class, User::getUsername, UserRepo::findByUsername);
	}
}

