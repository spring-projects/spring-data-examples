package example.repo;

import example.model.Customer1777;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1777Repository extends CrudRepository<Customer1777, Long> {

	List<Customer1777> findByLastName(String lastName);
}
