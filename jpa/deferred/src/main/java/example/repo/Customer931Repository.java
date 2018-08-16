package example.repo;

import example.model.Customer931;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer931Repository extends CrudRepository<Customer931, Long> {

	List<Customer931> findByLastName(String lastName);
}
