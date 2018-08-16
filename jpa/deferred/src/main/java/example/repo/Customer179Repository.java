package example.repo;

import example.model.Customer179;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer179Repository extends CrudRepository<Customer179, Long> {

	List<Customer179> findByLastName(String lastName);
}
