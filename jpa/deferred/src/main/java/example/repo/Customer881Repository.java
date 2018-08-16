package example.repo;

import example.model.Customer881;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer881Repository extends CrudRepository<Customer881, Long> {

	List<Customer881> findByLastName(String lastName);
}
