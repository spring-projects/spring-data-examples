package example.repo;

import example.model.Customer1363;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1363Repository extends CrudRepository<Customer1363, Long> {

	List<Customer1363> findByLastName(String lastName);
}
