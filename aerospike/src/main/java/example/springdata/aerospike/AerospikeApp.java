/**
 * 
 */
package example.springdata.aerospike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.aerospike.core.AerospikeTemplate;

import org.springframework.data.aerospike.repository.query.Criteria;
import org.springframework.data.aerospike.repository.query.Query;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.query.IndexType;

import example.springdata.aerospike.data.Person;

/**
 *
 *
 * @author Peter Milne
 * @author Jean Mercier
 *
 */
public class AerospikeApp {

	private static final Logger LOG = LoggerFactory
			.getLogger(AerospikeApp.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String localhost = "127.0.0.1";
			AerospikeClient client = new AerospikeClient(null, localhost, 3000);
			AerospikeTemplate aerospikeTemplate = new AerospikeTemplate(client,	"test");
			aerospikeTemplate.delete(Person.class);
			aerospikeTemplate.createIndex(Person.class,"Person_firstName_index_xx", "name", IndexType.STRING);
			Person personSven01 = new Person("Sven-01", "ZName", 25);
			Person personSven02 = new Person("Sven-02", "QName", 21);
			Person personSven03 = new Person("Sven-03", "AName", 24);
			Person personSven04 = new Person("Sven-04", "WName", 25);

			

			aerospikeTemplate.insert(personSven01);
			aerospikeTemplate.insert(personSven02);
			aerospikeTemplate.insert(personSven03);
			aerospikeTemplate.insert(personSven04);

			Query query = new Query(
					Criteria.where("Person").is("WName", "name"));

			Iterable<Person> it = aerospikeTemplate.find(query, Person.class);
			int count = 0;
			Person firstPerson = null;
			for (Person person : it) {
				firstPerson = person;
				LOG.info(firstPerson.toString());
				System.out.println(firstPerson.toString());
				count++;
			}
		}
		catch (AerospikeException e) {
			e.printStackTrace();
		}

	}

}
