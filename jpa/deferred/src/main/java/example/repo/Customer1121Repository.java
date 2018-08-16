package example.repo;

import example.model.Customer1121;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1121Repository extends CrudRepository<Customer1121, Long> {

	List<Customer1121> findByLastName(String lastName);
}
