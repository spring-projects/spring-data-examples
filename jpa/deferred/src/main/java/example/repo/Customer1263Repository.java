package example.repo;

import example.model.Customer1263;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1263Repository extends CrudRepository<Customer1263, Long> {

	List<Customer1263> findByLastName(String lastName);
}
