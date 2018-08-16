package example.repo;

import example.model.Customer1317;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1317Repository extends CrudRepository<Customer1317, Long> {

	List<Customer1317> findByLastName(String lastName);
}
