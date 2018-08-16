package example.repo;

import example.model.Customer487;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer487Repository extends CrudRepository<Customer487, Long> {

	List<Customer487> findByLastName(String lastName);
}
