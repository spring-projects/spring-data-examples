package example.repo;

import example.model.Customer83;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer83Repository extends CrudRepository<Customer83, Long> {

	List<Customer83> findByLastName(String lastName);
}
