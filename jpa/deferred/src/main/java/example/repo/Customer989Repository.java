package example.repo;

import example.model.Customer989;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer989Repository extends CrudRepository<Customer989, Long> {

	List<Customer989> findByLastName(String lastName);
}
