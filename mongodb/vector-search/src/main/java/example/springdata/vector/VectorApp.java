/*
 * Copyright 2025 the original author or authors.
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
package example.springdata.vector;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Vector;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class VectorApp {

    public static void main(String[] args) {
        SpringApplication.run(VectorApp.class, args);
    }

    @Component
    static class DbInitializer implements CommandLineRunner {

        private final CommentRepository repository;

        DbInitializer(CommentRepository repository) {
            this.repository = repository;
        }

        @Override
        public void run(String... args) {

            repository.deleteAll();

            repository.save(new Comment("de", "comment 'one'", Vector.of(0.1001f, 0.22345f, 0.33456f, 0.44567f, 0.55678f)));
            repository.save(new Comment("de", "comment 'two'", Vector.of(0.2001f, 0.32345f, 0.43456f, 0.54567f, 0.65678f)));
            repository.save(new Comment("en", "comment 'three'", Vector.of(0.9001f, 0.82345f, 0.73456f, 0.64567f, 0.55678f)));
            repository.save(new Comment("de", "comment 'four'", Vector.of(0.9001f, 0.92345f, 0.93456f, 0.94567f, 0.95678f)));
        }
    }
}
