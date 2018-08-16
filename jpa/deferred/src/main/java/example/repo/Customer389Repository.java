package example.repo;

import example.model.Customer389;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer389Repository extends CrudRepository<Customer389, Long> {

	List<Customer389> findByLastName(String lastName);
}
