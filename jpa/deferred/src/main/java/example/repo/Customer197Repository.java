package example.repo;

import example.model.Customer197;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer197Repository extends CrudRepository<Customer197, Long> {

	List<Customer197> findByLastName(String lastName);
}
