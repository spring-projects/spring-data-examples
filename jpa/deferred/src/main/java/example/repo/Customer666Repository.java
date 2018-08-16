package example.repo;

import example.model.Customer666;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer666Repository extends CrudRepository<Customer666, Long> {

	List<Customer666> findByLastName(String lastName);
}
