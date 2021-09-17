package example.springdata.jdbc.howto.bidirectionalinternal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BiDiInternalApplicationTests {

	@Autowired
	MinionRepository minions;

	@Test
	void biDi() {

		Minion bob = new Minion("Bob");
		bob.addToy(new Toy("Dolphin"));
		bob.addToy(new Toy("Tiger Duck"));

		minions.save(bob);

		Minion reloaded = minions.findById(bob.id).get();

		reloaded.showYourToys();
	}
}
