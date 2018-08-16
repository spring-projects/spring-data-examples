package example.repo;

import example.model.Customer433;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer433Repository extends CrudRepository<Customer433, Long> {

	List<Customer433> findByLastName(String lastName);
}
