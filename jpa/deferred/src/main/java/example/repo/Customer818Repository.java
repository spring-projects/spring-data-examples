package example.repo;

import example.model.Customer818;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer818Repository extends CrudRepository<Customer818, Long> {

	List<Customer818> findByLastName(String lastName);
}
