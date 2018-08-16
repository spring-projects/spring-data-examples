package example.repo;

import example.model.Customer1455;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1455Repository extends CrudRepository<Customer1455, Long> {

	List<Customer1455> findByLastName(String lastName);
}
