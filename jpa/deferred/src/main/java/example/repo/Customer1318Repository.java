package example.repo;

import example.model.Customer1318;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1318Repository extends CrudRepository<Customer1318, Long> {

	List<Customer1318> findByLastName(String lastName);
}
