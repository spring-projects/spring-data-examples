package example.repo;

import example.model.Customer1766;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1766Repository extends CrudRepository<Customer1766, Long> {

	List<Customer1766> findByLastName(String lastName);
}
