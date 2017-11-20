package example.springdata.couchbase.repository;

import example.springdata.couchbase.CouchbaseConfiguration;
import example.springdata.couchbase.model.Airline;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for basic CRUD operations
 *
 * @author Chandana Kithalagama
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CouchbaseConfiguration.class)
public class AirlineRepositoryIntegrationTest {

	@Autowired
	AirlineRepository airlineRepository;
	Airline a, b, c;
	List<Airline> airlineList;

	@Before
	public void setup() {
		a = new Airline(20000,"airline", "CK 1", "LK", "CMB", "CLK","Sri Lanka");
		b = new Airline(20001,"airline", "CK 2", "LK", "CMB", "CLK","Sri Lanka");
		c = new Airline(20002,"airline", "CK 3", "LK", "CMB", "CLK","Sri Lanka");

		airlineList = Arrays.asList(a, b, b);
	}

	@Test
	public void testSaveAirline() {
		Iterable<Airline> itr = airlineRepository.save(Arrays.asList(a));
		assertThat(itr).isNotNull();
		Airline airline = airlineRepository.findOne(a.getId());
		assertThat(airline).isNotNull();
		assertThat(airline).isEqualTo(a);
		assertThat(airline).isNotEqualTo(b);
		airlineRepository.delete(a.getId());
	}

	@Test
	public void testGetAllAirlines() {
		Iterable<Airline> itr = airlineRepository.save(airlineList);
		assertThat(itr).isNotNull();
		Iterable<Airline> itr2 = airlineRepository.findAll(Arrays.asList(a.getId(), b.getId(), c.getId()));
		assertThat(itr2).isNotNull();
		airlineRepository.delete(airlineList);
	}

	@Test
	public void testDeleteAirlines() {
		Iterable<Airline> itr = airlineRepository.save(Arrays.asList(a));
		assertThat(itr).isNotNull();
		airlineRepository.delete(a.getId());
		Airline airline = airlineRepository.findOne(a.getId());
		assertThat(airline).isNull();
	}

	@Test
	public void testGetByName() {
		Iterable<Airline> itr = airlineRepository.save(Arrays.asList(a));
		assertThat(itr).isNotNull();
		List<Airline> airlines = airlineRepository.findAirlineByName(a.getName());
		assertThat(airlines.size()).isGreaterThanOrEqualTo(1);
		assertThat(airlines.get(0)).isEqualTo(a);
		airlineRepository.delete(a.getId());
	}

	@After
	public void tearDown() {
		airlineRepository.delete(airlineList);
	}
}
