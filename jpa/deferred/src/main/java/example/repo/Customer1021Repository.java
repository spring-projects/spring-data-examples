package example.repo;

import example.model.Customer1021;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1021Repository extends CrudRepository<Customer1021, Long> {

	List<Customer1021> findByLastName(String lastName);
}
