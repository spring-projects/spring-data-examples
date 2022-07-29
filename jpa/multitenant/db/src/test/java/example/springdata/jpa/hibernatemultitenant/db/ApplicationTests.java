package example.springdata.jpa.hibernatemultitenant.db;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
class ApplicationTests {

	public static final String PIVOTAL = "PIVOTAL";
	public static final String VMWARE = "VMWARE";
	@Autowired Persons persons;

	@Autowired TransactionTemplate txTemplate;

	@Autowired TenantIdentifierResolver currentTenant;

	@Test
	void saveAndLoadPerson() {

		createPerson(PIVOTAL, "Adam");
		createPerson(VMWARE, "Eve");

		currentTenant.setCurrentTenant(VMWARE);
		assertThat(persons.findAll()).extracting(Person::getName).containsExactly("Eve");

		currentTenant.setCurrentTenant(PIVOTAL);
		assertThat(persons.findAll()).extracting(Person::getName).containsExactly("Adam");
	}

	private Person createPerson(String schema, String name) {

		currentTenant.setCurrentTenant(schema);

		Person adam = txTemplate.execute(tx -> {
			Person person = Persons.named(name);
			return persons.save(person);
		});

		assertThat(adam.getId()).isNotNull();
		return adam;
	}
}
