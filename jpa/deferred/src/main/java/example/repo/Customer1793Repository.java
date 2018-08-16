package example.repo;

import example.model.Customer1793;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1793Repository extends CrudRepository<Customer1793, Long> {

	List<Customer1793> findByLastName(String lastName);
}
