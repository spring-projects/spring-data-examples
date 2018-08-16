package example.repo;

import example.model.Customer1881;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1881Repository extends CrudRepository<Customer1881, Long> {

	List<Customer1881> findByLastName(String lastName);
}
