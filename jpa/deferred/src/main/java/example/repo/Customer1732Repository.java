package example.repo;

import example.model.Customer1732;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1732Repository extends CrudRepository<Customer1732, Long> {

	List<Customer1732> findByLastName(String lastName);
}
