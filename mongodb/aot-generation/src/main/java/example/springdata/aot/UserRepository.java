package example.springdata.aot;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Christoph Strobl
 * @since 2025/01
 */
public interface UserRepository extends CrudRepository<User, String> {

    @Query("{ 'username' :  { $regex: '?0.*', $options: 'i' } }")
    List<UserProjection> usersWithUsernamesStartingWith(String username);
}
