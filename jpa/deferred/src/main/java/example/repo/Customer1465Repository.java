package example.repo;

import example.model.Customer1465;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1465Repository extends CrudRepository<Customer1465, Long> {

	List<Customer1465> findByLastName(String lastName);
}
