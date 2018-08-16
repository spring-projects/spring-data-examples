package example.repo;

import example.model.Customer673;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer673Repository extends CrudRepository<Customer673, Long> {

	List<Customer673> findByLastName(String lastName);
}
