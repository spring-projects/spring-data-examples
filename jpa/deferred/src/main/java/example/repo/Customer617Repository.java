package example.repo;

import example.model.Customer617;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer617Repository extends CrudRepository<Customer617, Long> {

	List<Customer617> findByLastName(String lastName);
}
