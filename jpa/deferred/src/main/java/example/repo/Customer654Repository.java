package example.repo;

import example.model.Customer654;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer654Repository extends CrudRepository<Customer654, Long> {

	List<Customer654> findByLastName(String lastName);
}
