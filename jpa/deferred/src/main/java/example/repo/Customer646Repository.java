package example.repo;

import example.model.Customer646;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer646Repository extends CrudRepository<Customer646, Long> {

	List<Customer646> findByLastName(String lastName);
}
