package example.repo;

import example.model.Customer77;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer77Repository extends CrudRepository<Customer77, Long> {

	List<Customer77> findByLastName(String lastName);
}
