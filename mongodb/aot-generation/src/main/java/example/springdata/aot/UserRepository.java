package example.springdata.aot;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Christoph Strobl
 * @since 2025/01
 */
public interface UserRepository extends CrudRepository<User, String> {

    @Query("{ 'username' :  { $regex: '?0.*', $options: 'i' } }")
    List<UserProjection> usersWithUsernamesStartingWith(String username);

    User findUserByUsername(String username);

    List<User> findUserByLastnameLike(String name);
    Slice<User> findUserByUsernameAfter(String name, Pageable pageable);

    Page<UserProjection> findUserByLastnameStartingWith(String name, Pageable page);
}
