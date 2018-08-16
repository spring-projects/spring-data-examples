package example.repo;

import example.model.Customer1275;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1275Repository extends CrudRepository<Customer1275, Long> {

	List<Customer1275> findByLastName(String lastName);
}
