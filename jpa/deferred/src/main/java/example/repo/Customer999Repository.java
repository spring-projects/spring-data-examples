package example.repo;

import example.model.Customer999;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer999Repository extends CrudRepository<Customer999, Long> {

	List<Customer999> findByLastName(String lastName);
}
