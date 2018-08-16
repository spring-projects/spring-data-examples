package example.repo;

import example.model.Customer988;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer988Repository extends CrudRepository<Customer988, Long> {

	List<Customer988> findByLastName(String lastName);
}
