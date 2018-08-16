package example.repo;

import example.model.Customer1401;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1401Repository extends CrudRepository<Customer1401, Long> {

	List<Customer1401> findByLastName(String lastName);
}
