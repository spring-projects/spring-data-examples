package example.repo;

import example.model.Customer1699;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1699Repository extends CrudRepository<Customer1699, Long> {

	List<Customer1699> findByLastName(String lastName);
}
