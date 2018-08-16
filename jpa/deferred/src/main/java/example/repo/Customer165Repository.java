package example.repo;

import example.model.Customer165;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer165Repository extends CrudRepository<Customer165, Long> {

	List<Customer165> findByLastName(String lastName);
}
