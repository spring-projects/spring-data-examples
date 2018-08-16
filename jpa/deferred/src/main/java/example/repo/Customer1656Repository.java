package example.repo;

import example.model.Customer1656;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1656Repository extends CrudRepository<Customer1656, Long> {

	List<Customer1656> findByLastName(String lastName);
}
