package example.repo;

import example.model.Customer1448;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1448Repository extends CrudRepository<Customer1448, Long> {

	List<Customer1448> findByLastName(String lastName);
}
