package example.repo;

import example.model.Customer1420;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1420Repository extends CrudRepository<Customer1420, Long> {

	List<Customer1420> findByLastName(String lastName);
}
