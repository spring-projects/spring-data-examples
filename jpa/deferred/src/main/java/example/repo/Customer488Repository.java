package example.repo;

import example.model.Customer488;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer488Repository extends CrudRepository<Customer488, Long> {

	List<Customer488> findByLastName(String lastName);
}
