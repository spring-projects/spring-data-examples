package example.repo;

import example.model.Customer270;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer270Repository extends CrudRepository<Customer270, Long> {

	List<Customer270> findByLastName(String lastName);
}
