package example.repo;

import example.model.Customer1125;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1125Repository extends CrudRepository<Customer1125, Long> {

	List<Customer1125> findByLastName(String lastName);
}
