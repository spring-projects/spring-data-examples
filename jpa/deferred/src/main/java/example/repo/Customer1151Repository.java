package example.repo;

import example.model.Customer1151;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1151Repository extends CrudRepository<Customer1151, Long> {

	List<Customer1151> findByLastName(String lastName);
}
