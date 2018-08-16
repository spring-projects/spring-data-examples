package example.repo;

import example.model.Customer1621;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1621Repository extends CrudRepository<Customer1621, Long> {

	List<Customer1621> findByLastName(String lastName);
}
