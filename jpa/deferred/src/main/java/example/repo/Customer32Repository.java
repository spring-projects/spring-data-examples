package example.repo;

import example.model.Customer32;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer32Repository extends CrudRepository<Customer32, Long> {

	List<Customer32> findByLastName(String lastName);
}
