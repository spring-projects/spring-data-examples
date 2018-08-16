package example.repo;

import example.model.Customer1273;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1273Repository extends CrudRepository<Customer1273, Long> {

	List<Customer1273> findByLastName(String lastName);
}
