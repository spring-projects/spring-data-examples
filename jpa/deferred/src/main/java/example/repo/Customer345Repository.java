package example.repo;

import example.model.Customer345;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer345Repository extends CrudRepository<Customer345, Long> {

	List<Customer345> findByLastName(String lastName);
}
