package example.repo;

import example.model.Customer448;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer448Repository extends CrudRepository<Customer448, Long> {

	List<Customer448> findByLastName(String lastName);
}
