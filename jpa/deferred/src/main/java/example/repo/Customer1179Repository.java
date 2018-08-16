package example.repo;

import example.model.Customer1179;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1179Repository extends CrudRepository<Customer1179, Long> {

	List<Customer1179> findByLastName(String lastName);
}
