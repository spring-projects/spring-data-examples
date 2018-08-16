package example.repo;

import example.model.Customer1997;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1997Repository extends CrudRepository<Customer1997, Long> {

	List<Customer1997> findByLastName(String lastName);
}
