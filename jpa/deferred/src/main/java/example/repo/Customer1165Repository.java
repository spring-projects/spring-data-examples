package example.repo;

import example.model.Customer1165;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1165Repository extends CrudRepository<Customer1165, Long> {

	List<Customer1165> findByLastName(String lastName);
}
