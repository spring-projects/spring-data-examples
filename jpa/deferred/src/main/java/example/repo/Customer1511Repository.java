package example.repo;

import example.model.Customer1511;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1511Repository extends CrudRepository<Customer1511, Long> {

	List<Customer1511> findByLastName(String lastName);
}
