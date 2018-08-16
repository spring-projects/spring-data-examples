package example.repo;

import example.model.Customer1900;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1900Repository extends CrudRepository<Customer1900, Long> {

	List<Customer1900> findByLastName(String lastName);
}
