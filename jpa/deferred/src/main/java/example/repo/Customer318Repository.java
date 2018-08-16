package example.repo;

import example.model.Customer318;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer318Repository extends CrudRepository<Customer318, Long> {

	List<Customer318> findByLastName(String lastName);
}
