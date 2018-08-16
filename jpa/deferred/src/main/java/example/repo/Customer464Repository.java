package example.repo;

import example.model.Customer464;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer464Repository extends CrudRepository<Customer464, Long> {

	List<Customer464> findByLastName(String lastName);
}
