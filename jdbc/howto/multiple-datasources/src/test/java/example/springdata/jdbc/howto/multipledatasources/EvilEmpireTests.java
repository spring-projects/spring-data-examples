package example.springdata.jdbc.howto.multipledatasources;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
