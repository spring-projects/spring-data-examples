package example.repo;

import example.model.Customer185;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer185Repository extends CrudRepository<Customer185, Long> {

	List<Customer185> findByLastName(String lastName);
}
