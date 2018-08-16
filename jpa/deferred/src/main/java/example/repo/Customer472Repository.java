package example.repo;

import example.model.Customer472;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer472Repository extends CrudRepository<Customer472, Long> {

	List<Customer472> findByLastName(String lastName);
}
