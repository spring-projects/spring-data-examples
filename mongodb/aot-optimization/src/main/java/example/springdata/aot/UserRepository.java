/*
 * Copyright 2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
