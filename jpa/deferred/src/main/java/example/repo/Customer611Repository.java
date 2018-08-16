package example.repo;

import example.model.Customer611;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer611Repository extends CrudRepository<Customer611, Long> {

	List<Customer611> findByLastName(String lastName);
}
