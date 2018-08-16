package example.repo;

import example.model.Customer1895;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1895Repository extends CrudRepository<Customer1895, Long> {

	List<Customer1895> findByLastName(String lastName);
}
