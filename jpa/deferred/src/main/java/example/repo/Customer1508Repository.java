package example.repo;

import example.model.Customer1508;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1508Repository extends CrudRepository<Customer1508, Long> {

	List<Customer1508> findByLastName(String lastName);
}
