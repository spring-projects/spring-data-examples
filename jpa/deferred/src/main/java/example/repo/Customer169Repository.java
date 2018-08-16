package example.repo;

import example.model.Customer169;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer169Repository extends CrudRepository<Customer169, Long> {

	List<Customer169> findByLastName(String lastName);
}
