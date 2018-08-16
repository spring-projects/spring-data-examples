package example.repo;

import example.model.Customer1113;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1113Repository extends CrudRepository<Customer1113, Long> {

	List<Customer1113> findByLastName(String lastName);
}
