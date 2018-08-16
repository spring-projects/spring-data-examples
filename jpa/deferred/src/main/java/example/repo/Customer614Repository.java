package example.repo;

import example.model.Customer614;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer614Repository extends CrudRepository<Customer614, Long> {

	List<Customer614> findByLastName(String lastName);
}
