package example.repo;

import example.model.Customer170;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer170Repository extends CrudRepository<Customer170, Long> {

	List<Customer170> findByLastName(String lastName);
}
