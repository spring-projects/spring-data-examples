package example.repo;

import example.model.Customer1606;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1606Repository extends CrudRepository<Customer1606, Long> {

	List<Customer1606> findByLastName(String lastName);
}
