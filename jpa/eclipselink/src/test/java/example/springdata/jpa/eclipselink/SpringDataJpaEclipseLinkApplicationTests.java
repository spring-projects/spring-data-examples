/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.jpa.eclipselink;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

    @Test
    public void contextLoads() {
    }

    @Test
    public void testInsertRace() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:MM z");


        Date ascentStartDate = sdf.parse("08/15/2015 07:00 MDT");

        double ascentDistanceInKM = 21.4364621;

        Race pikesPeakAscent = new Race();
        pikesPeakAscent.setName("Pikes Peak Ascent");
        pikesPeakAscent.setDistance(ascentDistanceInKM);
        pikesPeakAscent.setDate(ascentStartDate);
        pikesPeakAscent.setDescription("Started in 1956 as a challenge between smokers and non-smokers, " +
                "the Pikes Peak Marathon is #3 on the list of longest running US marathons behind Boston and Yonkers.");

        pikesPeakAscent = raceRepository.save(pikesPeakAscent);

        assertNotNull(pikesPeakAscent.getId());

        List<Race> races = raceRepository.findByDate(ascentStartDate);
        assertThat(races.contains(pikesPeakAscent), is(true));
    }
}
