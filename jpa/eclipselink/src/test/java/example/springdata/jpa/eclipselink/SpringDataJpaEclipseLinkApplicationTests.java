package example.springdata.jpa.eclipselink;

import example.springdata.jpa.eclipselink.domain.Gender;
import example.springdata.jpa.eclipselink.domain.Participant;
import example.springdata.jpa.eclipselink.domain.Race;
import example.springdata.jpa.eclipselink.repositories.ParticipantRepository;
import example.springdata.jpa.eclipselink.repositories.RaceRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Test for Spring Data JPA EclipseLink w/ Static Weaving example
 *
 * @author Jeremy Rickard
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataJpaEclipseLinkApplication.class)
public class SpringDataJpaEclipseLinkApplicationTests {

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    ParticipantRepository participantRepository;

    Race pikesPeakAscent;

    Race pikesPeakMarathon;

    Race summerRoundUp;

    Race gardenOfTheGods10Mile;

    Participant one;

    Participant two;

    Participant three;

    Date ascentStartDate;

    SimpleDateFormat sdf;

    double ascentDistanceInKM = 21.4364621;

    @Before
    public void setUp() throws Exception {

        sdf = new SimpleDateFormat("MM/dd/yyyy HH:MM z");


        ascentStartDate = sdf.parse("08/15/2015 07:00 MDT");

        pikesPeakAscent = new Race();
        pikesPeakAscent.setName("Pikes Peak Ascent");
        pikesPeakAscent.setDistance(ascentDistanceInKM);
        pikesPeakAscent.setDate(ascentStartDate);
        pikesPeakAscent.setDescription("Started in 1956 as a challenge between smokers and non-smokers, " +
                "the Pikes Peak Marathon is #3 on the list of longest running US marathons behind Boston and Yonkers.");

        pikesPeakMarathon = new Race();
        pikesPeakMarathon.setName("Pikes Peak Marathon");
        pikesPeakMarathon.setDistance(ascentDistanceInKM * 2);
        pikesPeakMarathon.setDate(ascentStartDate);
        pikesPeakMarathon.setDescription("Started in 1956 as a challenge between smokers and non-smokers, " +
                "the Pikes Peak Marathon is #3 on the list of longest running US marathons behind Boston and Yonkers.");


        summerRoundUp = new Race();
        summerRoundUp.setName("Summer Round Up");
        summerRoundUp.setDescription("2nd Leg of the Triple Crown of Running");
        summerRoundUp.setDate(sdf.parse("07/12/2015 07:00 MDT"));
        summerRoundUp.setDistance(12.0);

        gardenOfTheGods10Mile = new Race();
        gardenOfTheGods10Mile.setName("Garden of the Gods 10 Miler");
        gardenOfTheGods10Mile.setDescription("1st Leg of the Triple Crown of Running");
        gardenOfTheGods10Mile.setDate(sdf.parse("06/15/2015 07:00 MDT"));
        gardenOfTheGods10Mile.setDistance(16.0934);


        one = new Participant();
        one.setAge(25);
        one.setEmailAddress("john.doe@gmail.com");
        one.setFirstName("John");
        one.setLastName("Doe");
        one.setPhoneNumber("1-123-234-5679");
        one.setGender(Gender.MALE);

        two = new Participant();
        two.setAge(21);
        two.setEmailAddress("jane.doe@gmail.com");
        two.setFirstName("Jane");
        two.setLastName("Doe");
        two.setPhoneNumber("1-123-234-5678");
        two.setGender(Gender.FEMALE);

        three = new Participant();
        three.setAge(62);
        three.setEmailAddress("sam.smith@gmail.com");
        three.setFirstName("Samantha");
        three.setLastName("Smith");
        three.setPhoneNumber("1-234-911-5678");
        three.setGender(Gender.FEMALE);


    }


    @Test
    public void contextLoads() {
    }

    @Test
    public void testInsertRace() {

        pikesPeakAscent = raceRepository.save(pikesPeakAscent);
        pikesPeakMarathon = raceRepository.save(pikesPeakMarathon);
        summerRoundUp = raceRepository.save(summerRoundUp);
        gardenOfTheGods10Mile = raceRepository.save(gardenOfTheGods10Mile);

        assertNotNull(pikesPeakAscent.getUuid());
        assertNotNull(pikesPeakMarathon.getUuid());
        assertNotNull(summerRoundUp.getUuid());
        assertNotNull(gardenOfTheGods10Mile.getUuid());

        assertNotEquals(pikesPeakAscent.getUuid(), pikesPeakMarathon.getUuid());
    }

    @Test
    public void testFindRacesByDates() {
        pikesPeakAscent = raceRepository.save(pikesPeakAscent);
        pikesPeakMarathon = raceRepository.save(pikesPeakMarathon);
        summerRoundUp = raceRepository.save(summerRoundUp);
        gardenOfTheGods10Mile = raceRepository.save(gardenOfTheGods10Mile);

        List<Race> races = raceRepository.findByDate(ascentStartDate);
        assertThat(races.contains(pikesPeakAscent), is(true));
        assertThat(races.contains(pikesPeakMarathon), is(true));

        races = raceRepository.findByDateBefore(ascentStartDate);
        assertThat(races.contains(pikesPeakAscent), is(false));
        assertThat(races.contains(pikesPeakMarathon), is(false));
    }

    @Test
    public void testFindRacesByDistance() {
        pikesPeakAscent = raceRepository.save(pikesPeakAscent);
        pikesPeakMarathon = raceRepository.save(pikesPeakMarathon);
        summerRoundUp = raceRepository.save(summerRoundUp);
        gardenOfTheGods10Mile = raceRepository.save(gardenOfTheGods10Mile);

        List<Race> races = raceRepository.findByDistanceBetween(20, 100);
        assertThat(races.contains(pikesPeakAscent), is(true));
        assertThat(races.contains(pikesPeakMarathon), is(true));

        races = raceRepository.findByDistanceBetween(10, 19);
        assertThat(races.contains(pikesPeakAscent), is(false));
        assertThat(races.contains(pikesPeakMarathon), is(false));
    }


    @Test
    public void testRaceByName() {
        pikesPeakAscent = raceRepository.save(pikesPeakAscent);
        pikesPeakMarathon = raceRepository.save(pikesPeakMarathon);
        summerRoundUp = raceRepository.save(summerRoundUp);
        gardenOfTheGods10Mile = raceRepository.save(gardenOfTheGods10Mile);

        List<Race> races = raceRepository.findByName("Summer Round Up");
        assertThat(races.contains(pikesPeakAscent), is(false));
        assertThat(races.contains(pikesPeakMarathon), is(false));
        assertThat(races.contains(summerRoundUp), is(true));
    }


    @Test
    public void testInsertParticipant() {

        one = participantRepository.save(one);
        two = participantRepository.save(two);
        three = participantRepository.save(three);

        assertNotNull(one.getUuid());
        assertNotNull(two.getUuid());
        assertNotNull(three.getUuid());

        assertNotEquals(one.getUuid(), two.getUuid());
        assertNotEquals(two.getUuid(), three.getUuid());

        assertThat(participantRepository.findAll().size(), is(3));

        List<Participant> twentySomethings = participantRepository.findByAgeLessThan(40);

        for (Participant participant : twentySomethings) {
            System.err.println(participant.getUuid());
        }

        System.err.println(one.getUuid());
        System.err.println(two.getUuid());
        System.err.println(three.getUuid());

        assertThat(twentySomethings.contains(one), is(true));

    }

    @Test
    public void testFindByAgeLessThan() {

        one = participantRepository.save(one);
        two = participantRepository.save(two);
        three = participantRepository.save(three);

        List<Participant> twentySomethings = participantRepository.findByAgeLessThan(40);
        assertThat(twentySomethings.contains(one), is(true));
        assertThat(twentySomethings.contains(three), is(false));
    }

    @Test
    public void testFindAgeBetween() {
        one = participantRepository.save(one);
        two = participantRepository.save(two);
        three = participantRepository.save(three);

        List<Participant> twentySomethings = participantRepository.findByAgeBetween(40, 70);
        assertThat(twentySomethings.contains(one), is(false));
        assertThat(twentySomethings.contains(three), is(true));
    }

    @Test
    public void testFindByLastName() {

        one = participantRepository.save(one);
        two = participantRepository.save(two);
        three = participantRepository.save(three);

        List<Participant> theDoes = participantRepository.findByLastName("Doe");

        assertThat(theDoes.contains(one), is(true));
        assertThat(theDoes.contains(three), is(false));

    }


    @After
    public void tearDown() {
        participantRepository.deleteAll();
        raceRepository.deleteAll();
    }


}
