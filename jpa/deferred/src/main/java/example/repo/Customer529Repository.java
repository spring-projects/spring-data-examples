package example.repo;

import example.model.Customer529;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer529Repository extends CrudRepository<Customer529, Long> {

	List<Customer529> findByLastName(String lastName);
}
