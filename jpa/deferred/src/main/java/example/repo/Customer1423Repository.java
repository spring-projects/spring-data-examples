package example.repo;

import example.model.Customer1423;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1423Repository extends CrudRepository<Customer1423, Long> {

	List<Customer1423> findByLastName(String lastName);
}
