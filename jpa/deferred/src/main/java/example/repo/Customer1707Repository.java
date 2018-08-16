package example.repo;

import example.model.Customer1707;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1707Repository extends CrudRepository<Customer1707, Long> {

	List<Customer1707> findByLastName(String lastName);
}
