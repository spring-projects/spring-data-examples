package example.repo;

import example.model.Customer465;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer465Repository extends CrudRepository<Customer465, Long> {

	List<Customer465> findByLastName(String lastName);
}
