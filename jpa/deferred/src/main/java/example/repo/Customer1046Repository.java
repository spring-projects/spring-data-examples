package example.repo;

import example.model.Customer1046;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1046Repository extends CrudRepository<Customer1046, Long> {

	List<Customer1046> findByLastName(String lastName);
}
