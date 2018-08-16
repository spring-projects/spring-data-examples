package example.repo;

import example.model.Customer1971;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1971Repository extends CrudRepository<Customer1971, Long> {

	List<Customer1971> findByLastName(String lastName);
}
