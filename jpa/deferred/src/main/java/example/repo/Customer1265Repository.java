package example.repo;

import example.model.Customer1265;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1265Repository extends CrudRepository<Customer1265, Long> {

	List<Customer1265> findByLastName(String lastName);
}
