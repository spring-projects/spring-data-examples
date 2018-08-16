package example.repo;

import example.model.Customer1142;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1142Repository extends CrudRepository<Customer1142, Long> {

	List<Customer1142> findByLastName(String lastName);
}
