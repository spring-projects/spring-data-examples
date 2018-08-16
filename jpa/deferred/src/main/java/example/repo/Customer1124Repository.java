package example.repo;

import example.model.Customer1124;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1124Repository extends CrudRepository<Customer1124, Long> {

	List<Customer1124> findByLastName(String lastName);
}
