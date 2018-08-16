package example.repo;

import example.model.Customer1484;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1484Repository extends CrudRepository<Customer1484, Long> {

	List<Customer1484> findByLastName(String lastName);
}
