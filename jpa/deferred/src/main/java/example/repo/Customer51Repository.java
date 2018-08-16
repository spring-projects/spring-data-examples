package example.repo;

import example.model.Customer51;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer51Repository extends CrudRepository<Customer51, Long> {

	List<Customer51> findByLastName(String lastName);
}
