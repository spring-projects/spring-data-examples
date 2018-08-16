package example.repo;

import example.model.Customer208;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer208Repository extends CrudRepository<Customer208, Long> {

	List<Customer208> findByLastName(String lastName);
}
