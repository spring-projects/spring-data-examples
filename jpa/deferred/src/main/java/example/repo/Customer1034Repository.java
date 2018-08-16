package example.repo;

import example.model.Customer1034;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1034Repository extends CrudRepository<Customer1034, Long> {

	List<Customer1034> findByLastName(String lastName);
}
