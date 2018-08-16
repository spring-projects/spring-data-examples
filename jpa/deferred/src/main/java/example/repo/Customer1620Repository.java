package example.repo;

import example.model.Customer1620;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1620Repository extends CrudRepository<Customer1620, Long> {

	List<Customer1620> findByLastName(String lastName);
}
