package example.repo;

import example.model.Customer1490;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1490Repository extends CrudRepository<Customer1490, Long> {

	List<Customer1490> findByLastName(String lastName);
}
