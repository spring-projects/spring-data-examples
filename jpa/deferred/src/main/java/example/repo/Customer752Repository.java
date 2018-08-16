package example.repo;

import example.model.Customer752;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer752Repository extends CrudRepository<Customer752, Long> {

	List<Customer752> findByLastName(String lastName);
}
