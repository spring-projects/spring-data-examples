package example.repo;

import example.model.Customer1267;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1267Repository extends CrudRepository<Customer1267, Long> {

	List<Customer1267> findByLastName(String lastName);
}
