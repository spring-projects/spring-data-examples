package example.springdata.rest.associations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = true)
public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}

