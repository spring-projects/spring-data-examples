package example.repo;

import example.model.Customer526;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer526Repository extends CrudRepository<Customer526, Long> {

	List<Customer526> findByLastName(String lastName);
}
