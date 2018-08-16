package example.repo;

import example.model.Customer1136;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1136Repository extends CrudRepository<Customer1136, Long> {

	List<Customer1136> findByLastName(String lastName);
}
