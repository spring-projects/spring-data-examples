package example.repo;

import example.model.Customer243;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer243Repository extends CrudRepository<Customer243, Long> {

	List<Customer243> findByLastName(String lastName);
}
