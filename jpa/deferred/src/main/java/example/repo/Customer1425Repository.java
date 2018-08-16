package example.repo;

import example.model.Customer1425;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1425Repository extends CrudRepository<Customer1425, Long> {

	List<Customer1425> findByLastName(String lastName);
}
