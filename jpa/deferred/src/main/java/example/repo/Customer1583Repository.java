package example.repo;

import example.model.Customer1583;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1583Repository extends CrudRepository<Customer1583, Long> {

	List<Customer1583> findByLastName(String lastName);
}
