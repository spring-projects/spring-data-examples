package example.repo;

import example.model.Customer70;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer70Repository extends CrudRepository<Customer70, Long> {

	List<Customer70> findByLastName(String lastName);
}
