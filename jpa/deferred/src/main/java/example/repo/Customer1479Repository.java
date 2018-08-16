package example.repo;

import example.model.Customer1479;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1479Repository extends CrudRepository<Customer1479, Long> {

	List<Customer1479> findByLastName(String lastName);
}
