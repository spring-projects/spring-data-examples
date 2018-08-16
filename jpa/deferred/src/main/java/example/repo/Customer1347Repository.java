package example.repo;

import example.model.Customer1347;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1347Repository extends CrudRepository<Customer1347, Long> {

	List<Customer1347> findByLastName(String lastName);
}
