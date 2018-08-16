package example.repo;

import example.model.Customer1788;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1788Repository extends CrudRepository<Customer1788, Long> {

	List<Customer1788> findByLastName(String lastName);
}
