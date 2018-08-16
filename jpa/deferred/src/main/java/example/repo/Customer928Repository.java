package example.repo;

import example.model.Customer928;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer928Repository extends CrudRepository<Customer928, Long> {

	List<Customer928> findByLastName(String lastName);
}
