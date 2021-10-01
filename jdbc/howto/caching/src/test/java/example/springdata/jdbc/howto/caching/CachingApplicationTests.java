package example.springdata.jdbc.howto.caching;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CachingApplicationTests {

	private Long bobsId;
	@Autowired MinionRepository minions;

	@BeforeEach
	void setup() {

		Minion bob = minions.save(new Minion("Bob"));
		bobsId = bob.id;
	}

	@Test
	void saveloadMultipleTimes() {

		Optional<Minion> bob = null;
		for (int i = 0; i < 10; i++) {
			bob = minions.findById(bobsId);
		}

		minions.save(bob.get());

		for (int i = 0; i < 10; i++) {
			bob = minions.findById(bobsId);
		}

	}

}
