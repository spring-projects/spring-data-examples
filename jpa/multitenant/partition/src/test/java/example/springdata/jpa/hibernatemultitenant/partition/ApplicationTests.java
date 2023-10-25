package example.springdata.jpa.hibernatemultitenant.partition;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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

	@AfterEach
	void afterEach() {
		currentTenant.setCurrentTenant(VMWARE);
		persons.deleteAll();
		currentTenant.setCurrentTenant(PIVOTAL);
		persons.deleteAll();
	}

	@Test
	void saveAndLoadPerson() {

		final Person adam = createPerson(PIVOTAL, "Adam");
		final Person eve = createPerson(VMWARE, "Eve");

		assertThat(adam.getTenant()).isEqualTo(PIVOTAL);
		assertThat(eve.getTenant()).isEqualTo(VMWARE);

		currentTenant.setCurrentTenant(VMWARE);
		assertThat(persons.findAll()).extracting(Person::getName).containsExactly("Eve");

		currentTenant.setCurrentTenant(PIVOTAL);
		assertThat(persons.findAll()).extracting(Person::getName).containsExactly("Adam");
	}

	@Test
	@Disabled("Find by Id no longer considers the current tenant")
	void findById() {

		final Person adam = createPerson(PIVOTAL, "Adam");
		final Person vAdam = createPerson(VMWARE, "Adam");

		currentTenant.setCurrentTenant(VMWARE);
		assertThat(persons.findById(vAdam.getId()).get().getTenant()).isEqualTo(VMWARE);
		assertThat(persons.findById(adam.getId())).isEmpty();
	}

	@Test
	void queryJPQL() {

		createPerson(PIVOTAL, "Adam");
		createPerson(VMWARE, "Adam");
		createPerson(VMWARE, "Eve");

		currentTenant.setCurrentTenant(VMWARE);
		assertThat(persons.findJpqlByName("Adam").getTenant()).isEqualTo(VMWARE);

		currentTenant.setCurrentTenant(PIVOTAL);
		assertThat(persons.findJpqlByName("Eve")).isNull();
	}

	@Test
	void querySQL() {

		createPerson(PIVOTAL, "Adam");
		createPerson(VMWARE, "Adam");

		currentTenant.setCurrentTenant(VMWARE);
		assertThatThrownBy(() -> persons.findSqlByName("Adam")).isInstanceOf(IncorrectResultSizeDataAccessException.class);
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
