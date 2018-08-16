package example.repo;

import example.model.Customer1524;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1524Repository extends CrudRepository<Customer1524, Long> {

	List<Customer1524> findByLastName(String lastName);
}
