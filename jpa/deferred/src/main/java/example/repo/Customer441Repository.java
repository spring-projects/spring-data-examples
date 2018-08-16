package example.repo;

import example.model.Customer441;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer441Repository extends CrudRepository<Customer441, Long> {

	List<Customer441> findByLastName(String lastName);
}
