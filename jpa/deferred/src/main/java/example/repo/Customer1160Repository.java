package example.repo;

import example.model.Customer1160;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1160Repository extends CrudRepository<Customer1160, Long> {

	List<Customer1160> findByLastName(String lastName);
}
