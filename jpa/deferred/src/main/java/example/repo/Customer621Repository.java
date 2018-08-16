package example.repo;

import example.model.Customer621;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer621Repository extends CrudRepository<Customer621, Long> {

	List<Customer621> findByLastName(String lastName);
}
