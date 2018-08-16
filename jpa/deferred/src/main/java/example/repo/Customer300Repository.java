package example.repo;

import example.model.Customer300;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer300Repository extends CrudRepository<Customer300, Long> {

	List<Customer300> findByLastName(String lastName);
}
