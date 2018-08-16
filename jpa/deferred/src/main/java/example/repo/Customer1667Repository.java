package example.repo;

import example.model.Customer1667;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1667Repository extends CrudRepository<Customer1667, Long> {

	List<Customer1667> findByLastName(String lastName);
}
