package example.repo;

import example.model.Customer967;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer967Repository extends CrudRepository<Customer967, Long> {

	List<Customer967> findByLastName(String lastName);
}
