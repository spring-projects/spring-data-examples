package example.springdata.jdbc.howto.idgeneration;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

@SpringBootApplication
class IdGenerationApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdGenerationApplication.class, args);
	}

	@Bean
	BeforeConvertCallback<StringIdMinion> beforeSaveCallback() {

		return (minion) -> {
			if (minion.id == null) {
				minion.id = UUID.randomUUID().toString();
			}
			return minion;
		};
	}

}
