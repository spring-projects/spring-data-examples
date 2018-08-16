package example.repo;

import example.model.Customer142;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer142Repository extends CrudRepository<Customer142, Long> {

	List<Customer142> findByLastName(String lastName);
}
