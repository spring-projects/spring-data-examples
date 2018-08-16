package example.repo;

import example.model.Customer641;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer641Repository extends CrudRepository<Customer641, Long> {

	List<Customer641> findByLastName(String lastName);
}
