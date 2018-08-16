package example.repo;

import example.model.Customer953;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer953Repository extends CrudRepository<Customer953, Long> {

	List<Customer953> findByLastName(String lastName);
}
