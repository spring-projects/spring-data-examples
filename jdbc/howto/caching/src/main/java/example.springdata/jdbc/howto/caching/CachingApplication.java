package example.springdata.jdbc.howto.caching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
class CachingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachingApplication.class, args);
	}

}
