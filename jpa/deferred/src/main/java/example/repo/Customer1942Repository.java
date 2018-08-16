package example.repo;

import example.model.Customer1942;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1942Repository extends CrudRepository<Customer1942, Long> {

	List<Customer1942> findByLastName(String lastName);
}
