package example.repo;

import example.model.Customer1185;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1185Repository extends CrudRepository<Customer1185, Long> {

	List<Customer1185> findByLastName(String lastName);
}
