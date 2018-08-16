package example.repo;

import example.model.Customer1850;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1850Repository extends CrudRepository<Customer1850, Long> {

	List<Customer1850> findByLastName(String lastName);
}
