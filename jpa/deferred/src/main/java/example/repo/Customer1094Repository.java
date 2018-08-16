package example.repo;

import example.model.Customer1094;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1094Repository extends CrudRepository<Customer1094, Long> {

	List<Customer1094> findByLastName(String lastName);
}
