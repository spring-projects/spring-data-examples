package example.repo;

import example.model.Customer867;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer867Repository extends CrudRepository<Customer867, Long> {

	List<Customer867> findByLastName(String lastName);
}
