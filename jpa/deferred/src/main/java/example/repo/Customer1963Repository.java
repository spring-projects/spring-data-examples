package example.repo;

import example.model.Customer1963;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1963Repository extends CrudRepository<Customer1963, Long> {

	List<Customer1963> findByLastName(String lastName);
}
