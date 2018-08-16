package example.repo;

import example.model.Customer1723;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1723Repository extends CrudRepository<Customer1723, Long> {

	List<Customer1723> findByLastName(String lastName);
}
