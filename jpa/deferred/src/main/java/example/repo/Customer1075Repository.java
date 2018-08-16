package example.repo;

import example.model.Customer1075;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1075Repository extends CrudRepository<Customer1075, Long> {

	List<Customer1075> findByLastName(String lastName);
}
