package example.repo;

import example.model.Customer326;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer326Repository extends CrudRepository<Customer326, Long> {

	List<Customer326> findByLastName(String lastName);
}
