package example.repo;

import example.model.Customer958;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer958Repository extends CrudRepository<Customer958, Long> {

	List<Customer958> findByLastName(String lastName);
}
