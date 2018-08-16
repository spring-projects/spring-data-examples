package example.repo;

import example.model.Customer382;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer382Repository extends CrudRepository<Customer382, Long> {

	List<Customer382> findByLastName(String lastName);
}
