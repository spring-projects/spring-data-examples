package example.repo;

import example.model.Customer348;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer348Repository extends CrudRepository<Customer348, Long> {

	List<Customer348> findByLastName(String lastName);
}
