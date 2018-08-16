package example.repo;

import example.model.Customer454;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer454Repository extends CrudRepository<Customer454, Long> {

	List<Customer454> findByLastName(String lastName);
}
