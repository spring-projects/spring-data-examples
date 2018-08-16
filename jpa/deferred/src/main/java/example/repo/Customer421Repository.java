package example.repo;

import example.model.Customer421;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer421Repository extends CrudRepository<Customer421, Long> {

	List<Customer421> findByLastName(String lastName);
}
