package example.repo;

import example.model.Customer1483;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1483Repository extends CrudRepository<Customer1483, Long> {

	List<Customer1483> findByLastName(String lastName);
}
