package example.repo;

import example.model.Customer43;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer43Repository extends CrudRepository<Customer43, Long> {

	List<Customer43> findByLastName(String lastName);
}
