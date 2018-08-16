package example.repo;

import example.model.Customer730;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer730Repository extends CrudRepository<Customer730, Long> {

	List<Customer730> findByLastName(String lastName);
}
