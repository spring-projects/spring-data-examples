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

        User leia = new User("id-2", "leia");
        leia.setFirstname("Leia");
        leia.setLastname("Organa");

        User han = new User("id-3", "han");
        han.setFirstname("Han");
        han.setLastname("Solo");

        User chewbacca = new User("id-4", "chewbacca");
        User yoda = new User("id-5", "yoda");
        User vader = new User("id-6", "vader");
        vader.setFirstname("Anakin");
        vader.setLastname("Skywalker");

        repository.saveAll(List.of(luke, leia, han, chewbacca, yoda, vader));

        System.out.println("-------");
        System.out.println(repository.usersWithUsernamesStartingWith("l"));
        System.out.println("-------");
        System.out.println(repository.findUserByUsername("yoda"));
        System.out.println("-------");
        System.out.println(repository.findUserByLastnameLike("Sky"));
        System.out.println("-------");


    }
}
