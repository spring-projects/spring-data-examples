package example.repo;

import example.model.Customer1216;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1216Repository extends CrudRepository<Customer1216, Long> {

	List<Customer1216> findByLastName(String lastName);
}
