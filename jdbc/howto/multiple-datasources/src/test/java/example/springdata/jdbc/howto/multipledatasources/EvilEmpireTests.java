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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Daniel Frey
 */
@SpringBootTest
public class EvilEmpireTests {

    @Autowired
    EvilEmpire subject;

    @Test
    void testMinionsForEvilMaster() {

        var actual = this.subject.getMinionsByEvilMaster( 1L );

        assertThat( actual )
                .hasSize( 5 )
                .containsExactlyInAnyOrder( "Stuart", "Kevin", "Bob", "Donnie", "Ken" );

    }

    @Test
    void testVerfifyIllegalArgumentExceptionThrownWhenEvilMasterNotFound() {

        assertThatThrownBy( () -> this.subject.getMinionsByEvilMaster( 2L ) )
                .isInstanceOf( IllegalArgumentException.class );

    }

}
