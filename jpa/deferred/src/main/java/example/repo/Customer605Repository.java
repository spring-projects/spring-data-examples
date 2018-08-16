package example.repo;

import example.model.Customer605;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer605Repository extends CrudRepository<Customer605, Long> {

	List<Customer605> findByLastName(String lastName);
}
