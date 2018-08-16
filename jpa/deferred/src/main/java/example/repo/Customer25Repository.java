package example.repo;

import example.model.Customer25;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer25Repository extends CrudRepository<Customer25, Long> {

	List<Customer25> findByLastName(String lastName);
}
