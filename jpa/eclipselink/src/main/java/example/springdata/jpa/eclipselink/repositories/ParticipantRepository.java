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
package example.springdata.jpa.eclipselink.repositories;


import example.springdata.jpa.eclipselink.domain.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository to manage {@link example.springdata.jpa.eclipselink.domain.Participant} instances.
 *
 * @author Jeremy Rickard
 */
public interface ParticipantRepository extends CrudRepository<Participant, String> {

    @Query("SELECT p from Participant p")
    List<Participant> findAll();

    List<Participant> findByLastName(String lastName);

    List<Participant> findByAgeLessThan(int age);

    List<Participant> findByAgeBetween(int age1, int age2);
}
