package example.repo;

import example.model.Customer1540;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1540Repository extends CrudRepository<Customer1540, Long> {

	List<Customer1540> findByLastName(String lastName);
}
