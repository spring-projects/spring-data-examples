package example.repo;

import example.model.Customer1177;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1177Repository extends CrudRepository<Customer1177, Long> {

	List<Customer1177> findByLastName(String lastName);
}
