package example.repo;

import example.model.Customer870;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer870Repository extends CrudRepository<Customer870, Long> {

	List<Customer870> findByLastName(String lastName);
}
