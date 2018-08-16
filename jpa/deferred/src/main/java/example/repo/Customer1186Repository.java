package example.repo;

import example.model.Customer1186;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1186Repository extends CrudRepository<Customer1186, Long> {

	List<Customer1186> findByLastName(String lastName);
}
