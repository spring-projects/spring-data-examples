package example.repo;

import example.model.Customer1744;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1744Repository extends CrudRepository<Customer1744, Long> {

	List<Customer1744> findByLastName(String lastName);
}
