package example.repo;

import example.model.Customer1919;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1919Repository extends CrudRepository<Customer1919, Long> {

	List<Customer1919> findByLastName(String lastName);
}
