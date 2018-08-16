package example.repo;

import example.model.Customer557;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer557Repository extends CrudRepository<Customer557, Long> {

	List<Customer557> findByLastName(String lastName);
}
