package example.repo;

import example.model.Customer1996;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1996Repository extends CrudRepository<Customer1996, Long> {

	List<Customer1996> findByLastName(String lastName);
}
