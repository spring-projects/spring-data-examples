package example.repo;

import example.model.Customer340;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer340Repository extends CrudRepository<Customer340, Long> {

	List<Customer340> findByLastName(String lastName);
}
