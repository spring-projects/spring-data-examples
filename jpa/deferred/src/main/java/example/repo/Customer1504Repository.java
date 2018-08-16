package example.repo;

import example.model.Customer1504;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1504Repository extends CrudRepository<Customer1504, Long> {

	List<Customer1504> findByLastName(String lastName);
}
