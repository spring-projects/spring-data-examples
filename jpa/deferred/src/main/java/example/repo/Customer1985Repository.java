package example.repo;

import example.model.Customer1985;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1985Repository extends CrudRepository<Customer1985, Long> {

	List<Customer1985> findByLastName(String lastName);
}
