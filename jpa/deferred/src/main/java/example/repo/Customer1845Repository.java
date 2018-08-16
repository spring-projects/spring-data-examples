package example.repo;

import example.model.Customer1845;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1845Repository extends CrudRepository<Customer1845, Long> {

	List<Customer1845> findByLastName(String lastName);
}
