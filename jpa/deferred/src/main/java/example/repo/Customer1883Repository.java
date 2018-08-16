package example.repo;

import example.model.Customer1883;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1883Repository extends CrudRepository<Customer1883, Long> {

	List<Customer1883> findByLastName(String lastName);
}
