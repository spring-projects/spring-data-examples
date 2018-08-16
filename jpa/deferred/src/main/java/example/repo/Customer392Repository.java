package example.repo;

import example.model.Customer392;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer392Repository extends CrudRepository<Customer392, Long> {

	List<Customer392> findByLastName(String lastName);
}
