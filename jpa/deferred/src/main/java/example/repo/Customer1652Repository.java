package example.repo;

import example.model.Customer1652;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1652Repository extends CrudRepository<Customer1652, Long> {

	List<Customer1652> findByLastName(String lastName);
}
