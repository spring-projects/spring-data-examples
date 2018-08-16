package example.repo;

import example.model.Customer866;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer866Repository extends CrudRepository<Customer866, Long> {

	List<Customer866> findByLastName(String lastName);
}
