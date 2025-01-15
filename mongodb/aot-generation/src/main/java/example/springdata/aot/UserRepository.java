package example.springdata.aot;

import java.util.List;
import java.util.Optional;

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

    User findUserByUsername(String username);

    Optional<User> findOptionalUserByUsername(String username);

    Long countUsersByLastnameLike(String lastname);

    Boolean existsByUsername(String username);

    List<User> findUserByLastnameLike(String lastname);

    List<User> findUserByPostsMessageLike(String part);

    List<User> findUserByLastnameLikeOrderByFirstname(String lastname);

    List<User> findTop2UsersByLastnameLike(String lastname);

    Slice<User> findUserByUsernameAfter(String username, Pageable pageable);

    Page<UserProjection> findUserByLastnameStartingWith(String lastname, Pageable page);

    @Query("{ 'username' :  { $regex: '?0.*', $options: 'i' } }")
    List<UserProjection> usersWithUsernamesStartingWith(String username);

    @Query(fields = "{ 'username' :  1 }", sort = "{ 'username' :  -1 }")
    List<User> findJustUsernameBy();

}
