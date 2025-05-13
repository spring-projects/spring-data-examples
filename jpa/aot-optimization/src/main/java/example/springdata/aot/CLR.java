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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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
		// Post lukeP1 = new Post("I have a bad feeling about this.");
		// em.persist(lukeP1);
		// luke.setPosts(List.of(lukeP1));

		User leia = new User("id-2", "leia");
		leia.setFirstname("Leia");
		leia.setLastname("Organa");

		User han = new User("id-3", "han");
		han.setFirstname("Han");
		han.setLastname("Solo");
		// Post hanP1 = new Post("It's the ship that made the Kessel Run in less than 12 Parsecs.");
		// em.persist(hanP1);
		// han.setPosts(List.of(hanP1));

		User chewbacca = new User("id-4", "chewbacca");
		User yoda = new User("id-5", "yoda");
		Post yodaP1 = new Post("Do. Or do not. There is no try.");
		Post yodaP2 = new Post(
				"Decide you must, how to serve them best. If you leave now, help them you could; but you would destroy all for which they have fought, and suffered.");
		// em.persist(yodaP1);
		// em.persist(yodaP2);
		// yoda.setPosts(List.of(yodaP1, yodaP2));

		User vader = new User("id-6", "vader");
		vader.setFirstname("Anakin");
		vader.setLastname("Skywalker");
		// Post vaderP1 = new Post("I am your father");
		// em.persist(vaderP1);
		// vader.setPosts(List.of(vaderP1));

		User kylo = new User("id-7", "kylo");
		kylo.setFirstname("Ben");
		kylo.setLastname("Solo");

		repository.saveAll(List.of(luke, leia, han, chewbacca, yoda, vader, kylo));

		System.out.println("------- annotated multi -------");
		System.out.println(repository.usersWithUsernamesStartingWith("l"));

		System.out.println("------- derived single -------");
		System.out.println(repository.findUserByUsername("yoda"));

		// System.out.println("------- derived nested.path -------");
		// System.out.println(repository.findUserByPostsMessageLike("father"));

		System.out.println("------- derived optional -------");
		System.out.println(repository.findOptionalUserByUsername("yoda"));

		System.out.println("------- derived count -------");
		Long count = repository.countUsersByLastnameLike("Sky");
		System.out.println("user count " + count);

		System.out.println("------- derived exists -------");
		Boolean exists = repository.existsByUsername("vader");
		System.out.println("user exists " + exists);

		System.out.println("------- derived multi -------");
		System.out.println(repository.findUserByLastnameStartingWith("Sky"));

		System.out.println("------- derived sorted -------");
		System.out.println(repository.findUserByLastnameStartingWithOrderByFirstname("Sky"));

		System.out.println("------- derived page -------");
		Page<User> page0 = repository.findUserByLastnameStartingWith("S", PageRequest.of(0, 2));
		System.out.println("page0: " + page0);
		System.out.println("page0.content: " + page0.getContent());

		Page<User> page1 = repository.findUserByLastnameStartingWith("S", PageRequest.of(1, 2));
		System.out.println("page1: " + page1);
		System.out.println("page1.content: " + page1.getContent());

		System.out.println("------- derived slice -------");
		Slice<User> slice0 = repository.findUserByUsernameAfter("luke", PageRequest.of(0, 2));
		System.out.println("slice0: " + slice0);
		System.out.println("slice0.content: " + slice0.getContent());

		System.out.println("------- derived top -------");
		System.out.println(repository.findTop2UsersByLastnameStartingWith("S"));

		// System.out.println("------- derived with fields -------");
		// System.out.println(repository.findJustUsernameBy());
	}
}
