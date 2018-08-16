package example.repo;

import example.model.Customer1692;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1692Repository extends CrudRepository<Customer1692, Long> {

	List<Customer1692> findByLastName(String lastName);
}
