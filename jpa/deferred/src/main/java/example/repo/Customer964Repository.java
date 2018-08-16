package example.repo;

import example.model.Customer964;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer964Repository extends CrudRepository<Customer964, Long> {

	List<Customer964> findByLastName(String lastName);
}
