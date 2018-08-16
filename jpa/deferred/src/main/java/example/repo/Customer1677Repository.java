package example.repo;

import example.model.Customer1677;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1677Repository extends CrudRepository<Customer1677, Long> {

	List<Customer1677> findByLastName(String lastName);
}
