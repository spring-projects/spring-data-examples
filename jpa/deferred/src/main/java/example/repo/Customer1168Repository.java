package example.repo;

import example.model.Customer1168;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1168Repository extends CrudRepository<Customer1168, Long> {

	List<Customer1168> findByLastName(String lastName);
}
