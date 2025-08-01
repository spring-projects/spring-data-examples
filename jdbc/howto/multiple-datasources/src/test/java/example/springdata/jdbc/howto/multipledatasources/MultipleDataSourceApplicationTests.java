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
package example.springdata.jdbc.howto.multipledatasources;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Daniel Frey
 */
@SpringBootTest
public class MultipleDataSourceApplicationTests {

    /**
     * Sanity Check - Verify configuration for multiple <code>DataSource</code>s and
     * Spring Data JDBC Repositories are handled correctly
     */
    @Test
    public void contextLoads() {

    }

}
