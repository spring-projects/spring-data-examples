package example.repo;

import example.model.Customer631;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer631Repository extends CrudRepository<Customer631, Long> {

	List<Customer631> findByLastName(String lastName);
}
