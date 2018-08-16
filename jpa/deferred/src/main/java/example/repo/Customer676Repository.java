package example.repo;

import example.model.Customer676;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer676Repository extends CrudRepository<Customer676, Long> {

	List<Customer676> findByLastName(String lastName);
}
