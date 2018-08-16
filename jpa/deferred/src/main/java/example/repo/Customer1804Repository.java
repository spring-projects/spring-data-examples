package example.repo;

import example.model.Customer1804;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1804Repository extends CrudRepository<Customer1804, Long> {

	List<Customer1804> findByLastName(String lastName);
}
