package example.repo;

import example.model.Customer1717;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1717Repository extends CrudRepository<Customer1717, Long> {

	List<Customer1717> findByLastName(String lastName);
}
