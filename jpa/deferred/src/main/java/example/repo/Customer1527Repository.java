package example.repo;

import example.model.Customer1527;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1527Repository extends CrudRepository<Customer1527, Long> {

	List<Customer1527> findByLastName(String lastName);
}
