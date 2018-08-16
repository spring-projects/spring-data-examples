package example.repo;

import example.model.Customer1765;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1765Repository extends CrudRepository<Customer1765, Long> {

	List<Customer1765> findByLastName(String lastName);
}
