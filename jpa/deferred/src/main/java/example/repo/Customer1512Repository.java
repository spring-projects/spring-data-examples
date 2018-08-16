package example.repo;

import example.model.Customer1512;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1512Repository extends CrudRepository<Customer1512, Long> {

	List<Customer1512> findByLastName(String lastName);
}
