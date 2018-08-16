package example.repo;

import example.model.Customer180;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer180Repository extends CrudRepository<Customer180, Long> {

	List<Customer180> findByLastName(String lastName);
}
