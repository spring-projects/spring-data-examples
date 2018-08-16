package example.repo;

import example.model.Customer1382;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1382Repository extends CrudRepository<Customer1382, Long> {

	List<Customer1382> findByLastName(String lastName);
}
