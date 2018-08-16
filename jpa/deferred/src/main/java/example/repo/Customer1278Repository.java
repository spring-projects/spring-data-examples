package example.repo;

import example.model.Customer1278;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1278Repository extends CrudRepository<Customer1278, Long> {

	List<Customer1278> findByLastName(String lastName);
}
