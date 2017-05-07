package example.springdata.couchbase.repository;

import example.springdata.couchbase.config.CouchbaseConfiguration;
import example.springdata.couchbase.model.Airline;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CouchbaseConfiguration.class)
public class AirlineRepositoryIntegrationTest {

    @Autowired
    private AirlineRepository airlineRepository;
    private Airline a, b, c;
    private List<Airline> airlineList;

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
        Assert.assertThat(itr, Matchers.notNullValue());
        Airline airline = airlineRepository.findOne(a.getId());
        Assert.assertThat(airline, Matchers.notNullValue());
        Assert.assertThat(airline, Matchers.is(a));
        Assert.assertThat(airline, Matchers.not(b));
        airlineRepository.delete(a.getId());
    }

    @Test
    public void testGetAllAirlines() {
        Iterable<Airline> itr = airlineRepository.save(airlineList);
        Assert.assertThat(itr, Matchers.notNullValue());
        Iterable<Airline> itr2 = airlineRepository.findAll(Arrays.asList(a.getId(), b.getId(), c.getId()));
        Assert.assertThat(itr2, Matchers.notNullValue());
        airlineRepository.delete(airlineList);
    }

    @Test
    public void testDeleteAirlines() {
        Iterable<Airline> itr = airlineRepository.save(Arrays.asList(a));
        Assert.assertThat(itr, Matchers.notNullValue());
        airlineRepository.delete(a.getId());
        Airline airline = airlineRepository.findOne(a.getId());
        Assert.assertThat(airline, Matchers.nullValue());
    }

    @Test
    public void testGetByName() {
        Iterable<Airline> itr = airlineRepository.save(Arrays.asList(a));
        Assert.assertThat(itr, Matchers.notNullValue());
        List<Airline> airlines = airlineRepository.findAirlineByName(a.getName());
        Assert.assertThat(airlines.size(), Matchers.greaterThanOrEqualTo(1));
        Assert.assertThat(airlines.get(0), Matchers.is(a));
        airlineRepository.delete(a.getId());
    }

    @After
    public void tearDown() {
        airlineRepository.delete(airlineList);
    }
}
