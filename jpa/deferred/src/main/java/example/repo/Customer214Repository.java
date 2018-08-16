package example.repo;

import example.model.Customer214;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer214Repository extends CrudRepository<Customer214, Long> {

	List<Customer214> findByLastName(String lastName);
}
