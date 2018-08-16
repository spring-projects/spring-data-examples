package example.repo;

import example.model.Customer570;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer570Repository extends CrudRepository<Customer570, Long> {

	List<Customer570> findByLastName(String lastName);
}
