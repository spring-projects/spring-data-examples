package example.repo;

import example.model.Customer1474;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1474Repository extends CrudRepository<Customer1474, Long> {

	List<Customer1474> findByLastName(String lastName);
}
