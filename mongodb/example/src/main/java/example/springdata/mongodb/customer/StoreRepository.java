package example.springdata.mongodb.customer;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for {@link Store} instances.
 *
 * @author Rishabh Saraswat
 */
interface StoreRepository extends CrudRepository<Store, String>{
}
