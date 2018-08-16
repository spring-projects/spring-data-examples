package example.repo;

import example.model.Customer569;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer569Repository extends CrudRepository<Customer569, Long> {

	List<Customer569> findByLastName(String lastName);
}
