package example.repo;

import example.model.Customer15;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer15Repository extends CrudRepository<Customer15, Long> {

	List<Customer15> findByLastName(String lastName);
}
