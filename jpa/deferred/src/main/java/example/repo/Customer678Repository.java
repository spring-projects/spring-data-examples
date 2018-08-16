package example.repo;

import example.model.Customer678;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer678Repository extends CrudRepository<Customer678, Long> {

	List<Customer678> findByLastName(String lastName);
}
