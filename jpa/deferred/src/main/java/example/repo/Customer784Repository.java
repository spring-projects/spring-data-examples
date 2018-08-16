package example.repo;

import example.model.Customer784;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer784Repository extends CrudRepository<Customer784, Long> {

	List<Customer784> findByLastName(String lastName);
}
