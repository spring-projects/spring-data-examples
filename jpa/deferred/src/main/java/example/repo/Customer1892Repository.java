package example.repo;

import example.model.Customer1892;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1892Repository extends CrudRepository<Customer1892, Long> {

	List<Customer1892> findByLastName(String lastName);
}
