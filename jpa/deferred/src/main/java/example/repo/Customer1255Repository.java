package example.repo;

import example.model.Customer1255;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1255Repository extends CrudRepository<Customer1255, Long> {

	List<Customer1255> findByLastName(String lastName);
}
