/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.aot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Christoph Strobl
 * @since 2025/01
 */
@Component
public class CLR implements CommandLineRunner {

    @Autowired UserRepository repository;

    @Override
    public void run(String... args) throws Exception {

        User luke = new User("id-1", "luke");
        luke.setFirstname("Luke");
        luke.setLastname("Skywalker");
        luke.setPosts(List.of(new Post("I have a bad feeling about this.")));

        User leia = new User("id-2", "leia");
        leia.setFirstname("Leia");
        leia.setLastname("Organa");

        User han = new User("id-3", "han");
        han.setFirstname("Han");
        han.setLastname("Solo");
        han.setPosts(List.of(new Post("It's the ship that made the Kessel Run in less than 12 Parsecs.")));

        User chewbacca = new User("id-4", "chewbacca");
        User yoda = new User("id-5", "yoda");
        yoda.setPosts(List.of(new Post("Do. Or do not. There is no try."), new Post("Decide you must, how to serve them best. If you leave now, help them you could; but you would destroy all for which they have fought, and suffered.")));

        User vader = new User("id-6", "vader");
        vader.setFirstname("Anakin");
        vader.setLastname("Skywalker");
        vader.setPosts(List.of(new Post("I am your father")));

        User kylo = new User("id-7", "kylo");
        kylo.setFirstname("Ben");
        kylo.setLastname("Solo");

        repository.saveAll(List.of(luke, leia, han, chewbacca, yoda, vader, kylo));

				repository.usersWithUsernamesStartingWith("l");

				repository.findUserByUsername("yoda");

				repository.findUserByPostsMessageLike("father");

				repository.findOptionalUserByUsername("yoda");

        Long count = repository.countUsersByLastnameLike("Sky");

        Boolean exists = repository.existsByUsername("vader");
				repository.findUserByLastnameLike("Sky");

    }
}
