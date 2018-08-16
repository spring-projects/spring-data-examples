package example.repo;

import example.model.Customer1550;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1550Repository extends CrudRepository<Customer1550, Long> {

	List<Customer1550> findByLastName(String lastName);
}
