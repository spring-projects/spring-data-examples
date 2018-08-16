package example.repo;

import example.model.Customer1708;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1708Repository extends CrudRepository<Customer1708, Long> {

	List<Customer1708> findByLastName(String lastName);
}
