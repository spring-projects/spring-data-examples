package example.repo;

import example.model.Customer732;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer732Repository extends CrudRepository<Customer732, Long> {

	List<Customer732> findByLastName(String lastName);
}
