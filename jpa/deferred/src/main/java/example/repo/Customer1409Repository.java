package example.repo;

import example.model.Customer1409;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1409Repository extends CrudRepository<Customer1409, Long> {

	List<Customer1409> findByLastName(String lastName);
}
