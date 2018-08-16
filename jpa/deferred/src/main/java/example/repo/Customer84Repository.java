package example.repo;

import example.model.Customer84;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer84Repository extends CrudRepository<Customer84, Long> {

	List<Customer84> findByLastName(String lastName);
}
