package example.repo;

import example.model.Customer1055;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1055Repository extends CrudRepository<Customer1055, Long> {

	List<Customer1055> findByLastName(String lastName);
}
