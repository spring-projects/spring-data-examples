package example.repo;

import example.model.Customer1303;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1303Repository extends CrudRepository<Customer1303, Long> {

	List<Customer1303> findByLastName(String lastName);
}
