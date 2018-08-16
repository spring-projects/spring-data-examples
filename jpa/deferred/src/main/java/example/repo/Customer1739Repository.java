package example.repo;

import example.model.Customer1739;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1739Repository extends CrudRepository<Customer1739, Long> {

	List<Customer1739> findByLastName(String lastName);
}
