package example.repo;

import example.model.Customer1676;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1676Repository extends CrudRepository<Customer1676, Long> {

	List<Customer1676> findByLastName(String lastName);
}
