package example.repo;

import example.model.Customer1752;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1752Repository extends CrudRepository<Customer1752, Long> {

	List<Customer1752> findByLastName(String lastName);
}
