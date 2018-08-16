package example.repo;

import example.model.Customer1611;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1611Repository extends CrudRepository<Customer1611, Long> {

	List<Customer1611> findByLastName(String lastName);
}
