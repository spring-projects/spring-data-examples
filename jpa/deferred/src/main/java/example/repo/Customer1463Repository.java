package example.repo;

import example.model.Customer1463;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1463Repository extends CrudRepository<Customer1463, Long> {

	List<Customer1463> findByLastName(String lastName);
}
