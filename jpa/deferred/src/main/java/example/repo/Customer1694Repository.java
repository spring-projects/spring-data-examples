package example.repo;

import example.model.Customer1694;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1694Repository extends CrudRepository<Customer1694, Long> {

	List<Customer1694> findByLastName(String lastName);
}
