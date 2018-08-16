package example.repo;

import example.model.Customer968;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer968Repository extends CrudRepository<Customer968, Long> {

	List<Customer968> findByLastName(String lastName);
}
