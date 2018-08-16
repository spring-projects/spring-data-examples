package example.repo;

import example.model.Customer1458;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1458Repository extends CrudRepository<Customer1458, Long> {

	List<Customer1458> findByLastName(String lastName);
}
