package example.repo;

import example.model.Customer622;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer622Repository extends CrudRepository<Customer622, Long> {

	List<Customer622> findByLastName(String lastName);
}
