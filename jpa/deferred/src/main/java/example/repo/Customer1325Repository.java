package example.repo;

import example.model.Customer1325;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1325Repository extends CrudRepository<Customer1325, Long> {

	List<Customer1325> findByLastName(String lastName);
}
