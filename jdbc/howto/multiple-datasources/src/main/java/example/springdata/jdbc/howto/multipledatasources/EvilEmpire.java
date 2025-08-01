/*
 * Copyright 2013-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.jdbc.howto.multipledatasources;

import example.springdata.jdbc.howto.multipledatasources.minion.Minion;
import example.springdata.jdbc.howto.multipledatasources.minion.MinionRepository;
import example.springdata.jdbc.howto.multipledatasources.person.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sample `Service` that uses both Spring Data JDBC Repositories
 *
 * @author Daniel Frey
 */
@Component
public class EvilEmpire {

    private final PersonRepository personRepository;
    private final MinionRepository minionRepository;

    public EvilEmpire( PersonRepository personRepository, MinionRepository minionRepository ) {

        this.personRepository = personRepository;
        this.minionRepository = minionRepository;

    }

    public List<String> getMinionsByEvilMaster( Long evilMaster ) {

        var person = this.personRepository.findById( evilMaster );
        if ( person.isPresent() ) {

            return this.minionRepository.findByEvilMaster( person.get().id() ).stream()
                    .map( Minion::name )
                    .toList();

        }

        throw new IllegalArgumentException( "No Minions found for Evil Master [" + evilMaster + "]" );
    }

}
