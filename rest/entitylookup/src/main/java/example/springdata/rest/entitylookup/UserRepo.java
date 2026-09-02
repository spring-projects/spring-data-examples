package example.springdata.rest.entitylookup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * @author Steve Rutherford
 */
@RepositoryRestResource(exported = true)
public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}

