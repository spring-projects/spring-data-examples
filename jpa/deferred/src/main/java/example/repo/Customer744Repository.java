package example.repo;

import example.model.Customer744;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer744Repository extends CrudRepository<Customer744, Long> {

	List<Customer744> findByLastName(String lastName);
}
