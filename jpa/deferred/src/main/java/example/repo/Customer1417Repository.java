package example.repo;

import example.model.Customer1417;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1417Repository extends CrudRepository<Customer1417, Long> {

	List<Customer1417> findByLastName(String lastName);
}
