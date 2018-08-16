package example.repo;

import example.model.Customer1277;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1277Repository extends CrudRepository<Customer1277, Long> {

	List<Customer1277> findByLastName(String lastName);
}
