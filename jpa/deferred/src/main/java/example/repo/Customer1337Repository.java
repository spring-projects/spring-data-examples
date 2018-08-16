package example.repo;

import example.model.Customer1337;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1337Repository extends CrudRepository<Customer1337, Long> {

	List<Customer1337> findByLastName(String lastName);
}
