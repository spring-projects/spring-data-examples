package example.repo;

import example.model.Customer1755;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1755Repository extends CrudRepository<Customer1755, Long> {

	List<Customer1755> findByLastName(String lastName);
}
