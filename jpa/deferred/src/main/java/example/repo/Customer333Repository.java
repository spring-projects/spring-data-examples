package example.repo;

import example.model.Customer333;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer333Repository extends CrudRepository<Customer333, Long> {

	List<Customer333> findByLastName(String lastName);
}
