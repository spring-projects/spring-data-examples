package example.repo;

import example.model.Customer1545;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1545Repository extends CrudRepository<Customer1545, Long> {

	List<Customer1545> findByLastName(String lastName);
}
