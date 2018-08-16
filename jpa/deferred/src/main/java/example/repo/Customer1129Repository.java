package example.repo;

import example.model.Customer1129;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1129Repository extends CrudRepository<Customer1129, Long> {

	List<Customer1129> findByLastName(String lastName);
}
