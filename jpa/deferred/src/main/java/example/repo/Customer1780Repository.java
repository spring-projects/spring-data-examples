package example.repo;

import example.model.Customer1780;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1780Repository extends CrudRepository<Customer1780, Long> {

	List<Customer1780> findByLastName(String lastName);
}
