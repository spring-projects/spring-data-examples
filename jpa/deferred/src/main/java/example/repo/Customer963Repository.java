package example.repo;

import example.model.Customer963;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer963Repository extends CrudRepository<Customer963, Long> {

	List<Customer963> findByLastName(String lastName);
}
