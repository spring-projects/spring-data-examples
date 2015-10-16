/**
 * 
 */
package example.springdata.aerospike;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.aerospike.core.AerospikeOperations;
import org.springframework.data.aerospike.core.AerospikeTemplate;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.query.IndexType;

import example.springdata.aerospike.config.TestRepositoryConfig;
import example.springdata.aerospike.data.Person;

/**
 *
 *
 * @author Peter Milne
 * @author Jean Mercier
 *
 */
public class RepositoryExample {

	@Autowired
	protected PersonRepository repository;

	@Autowired
	AerospikeOperations aerospikeOperations;

	@Autowired
	AerospikeClient client;

	/**
	 * @param ctx
	 */
	public RepositoryExample(ApplicationContext ctx) {
		aerospikeOperations = ctx.getBean(AerospikeTemplate.class);
		repository = (PersonRepository) ctx.getBean("personRepository");
		client = ctx.getBean(AerospikeClient.class);
	}

	/**
	 * @param args
	 */
	protected void setUp() {

		repository.deleteAll();

		Person dave = new Person("Dave-01", "Matthews", 42);
		Person donny = new Person("Dave-02", "Macintire", 39);
		Person oliver = new Person("Oliver-01", "Matthews", 4);
		Person carter = new Person("Carter-01", "Beauford", 49);
		Person boyd = new Person("Boyd-01", "Tinsley", 45);
		Person stefan = new Person("Stefan-01", "Lessard", 34);
		Person leroi = new Person("Leroi-01", "Moore", 41);
		Person leroi2 = new Person("Leroi-02", "Moore", 25);
		Person alicia = new Person("Alicia-01", "Keys", 30);

		repository.createIndex(Person.class, "person_name_index_repository", "name",IndexType.STRING);

		List<Person> all = (List<Person>) repository.save(Arrays.asList(oliver,
				dave, donny, carter, boyd, stefan, leroi, leroi2, alicia));

	}

	/**
	 * @param args
	 */
	protected void cleanUp() {

		repository.deleteAll();

	}

	/**
	 * 
	 */
	protected void executeRepositoryCall() {
		List<Person> result = repository.findByName("Beauford");
		System.out.println("Results for exact match of 'Beauford'");
		for (Person person : result) {
			System.out.println(person.toString());
		}
		System.out.println("Results for name startting with letter 'M'");
		List<Person> resultPartial = repository.findByNameStartsWith("M");
		for (Person person : resultPartial) {
			System.out.println(person.toString());
		}
	}

	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestRepositoryConfig.class);
		RepositoryExample repositoryExample = new RepositoryExample(ctx);
		repositoryExample.setUp();
		repositoryExample.executeRepositoryCall();
		repositoryExample.cleanUp();

	}

}
