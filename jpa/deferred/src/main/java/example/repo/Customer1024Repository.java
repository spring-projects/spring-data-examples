package example.repo;

import example.model.Customer1024;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1024Repository extends CrudRepository<Customer1024, Long> {

	List<Customer1024> findByLastName(String lastName);
}
