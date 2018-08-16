package example.repo;

import example.model.Customer1431;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1431Repository extends CrudRepository<Customer1431, Long> {

	List<Customer1431> findByLastName(String lastName);
}
