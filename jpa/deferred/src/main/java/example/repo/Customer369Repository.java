package example.repo;

import example.model.Customer369;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer369Repository extends CrudRepository<Customer369, Long> {

	List<Customer369> findByLastName(String lastName);
}
