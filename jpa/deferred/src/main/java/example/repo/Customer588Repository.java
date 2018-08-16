package example.repo;

import example.model.Customer588;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer588Repository extends CrudRepository<Customer588, Long> {

	List<Customer588> findByLastName(String lastName);
}
