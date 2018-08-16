package example.repo;

import example.model.Customer1364;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1364Repository extends CrudRepository<Customer1364, Long> {

	List<Customer1364> findByLastName(String lastName);
}
