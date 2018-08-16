package example.repo;

import example.model.Customer685;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer685Repository extends CrudRepository<Customer685, Long> {

	List<Customer685> findByLastName(String lastName);
}
