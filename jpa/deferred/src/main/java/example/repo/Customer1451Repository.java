package example.repo;

import example.model.Customer1451;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1451Repository extends CrudRepository<Customer1451, Long> {

	List<Customer1451> findByLastName(String lastName);
}
