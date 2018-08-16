package example.repo;

import example.model.Customer159;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer159Repository extends CrudRepository<Customer159, Long> {

	List<Customer159> findByLastName(String lastName);
}
