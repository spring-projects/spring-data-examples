package example.repo;

import example.model.Customer1306;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1306Repository extends CrudRepository<Customer1306, Long> {

	List<Customer1306> findByLastName(String lastName);
}
