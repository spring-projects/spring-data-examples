package example.repo;

import example.model.Customer936;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer936Repository extends CrudRepository<Customer936, Long> {

	List<Customer936> findByLastName(String lastName);
}
