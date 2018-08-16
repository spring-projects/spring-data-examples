package example.repo;

import example.model.Customer1144;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1144Repository extends CrudRepository<Customer1144, Long> {

	List<Customer1144> findByLastName(String lastName);
}
