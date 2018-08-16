package example.repo;

import example.model.Customer44;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer44Repository extends CrudRepository<Customer44, Long> {

	List<Customer44> findByLastName(String lastName);
}
