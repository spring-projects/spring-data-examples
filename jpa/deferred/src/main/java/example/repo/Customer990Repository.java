package example.repo;

import example.model.Customer990;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer990Repository extends CrudRepository<Customer990, Long> {

	List<Customer990> findByLastName(String lastName);
}
