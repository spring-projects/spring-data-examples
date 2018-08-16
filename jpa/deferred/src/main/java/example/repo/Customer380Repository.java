package example.repo;

import example.model.Customer380;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer380Repository extends CrudRepository<Customer380, Long> {

	List<Customer380> findByLastName(String lastName);
}
