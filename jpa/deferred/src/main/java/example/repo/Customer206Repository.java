package example.repo;

import example.model.Customer206;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer206Repository extends CrudRepository<Customer206, Long> {

	List<Customer206> findByLastName(String lastName);
}
