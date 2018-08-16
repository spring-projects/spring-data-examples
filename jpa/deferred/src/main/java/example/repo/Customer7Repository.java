package example.repo;

import example.model.Customer7;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer7Repository extends CrudRepository<Customer7, Long> {

	List<Customer7> findByLastName(String lastName);
}
