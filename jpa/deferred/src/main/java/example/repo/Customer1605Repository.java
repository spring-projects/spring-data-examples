package example.repo;

import example.model.Customer1605;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1605Repository extends CrudRepository<Customer1605, Long> {

	List<Customer1605> findByLastName(String lastName);
}
