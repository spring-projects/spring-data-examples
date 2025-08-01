package example.springdata.jdbc.howto.multipledatasources;

import example.springdata.jdbc.howto.multipledatasources.minion.Minion;
import example.springdata.jdbc.howto.multipledatasources.minion.MinionRepository;
import example.springdata.jdbc.howto.multipledatasources.person.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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
