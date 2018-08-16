package example.repo;

import example.model.Customer1028;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1028Repository extends CrudRepository<Customer1028, Long> {

	List<Customer1028> findByLastName(String lastName);
}
