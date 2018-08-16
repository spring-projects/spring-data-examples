package example.repo;

import example.model.Customer1270;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1270Repository extends CrudRepository<Customer1270, Long> {

	List<Customer1270> findByLastName(String lastName);
}
