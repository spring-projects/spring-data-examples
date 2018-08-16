package example.repo;

import example.model.Customer1712;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1712Repository extends CrudRepository<Customer1712, Long> {

	List<Customer1712> findByLastName(String lastName);
}
