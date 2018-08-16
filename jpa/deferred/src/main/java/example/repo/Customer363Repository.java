package example.repo;

import example.model.Customer363;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer363Repository extends CrudRepository<Customer363, Long> {

	List<Customer363> findByLastName(String lastName);
}
