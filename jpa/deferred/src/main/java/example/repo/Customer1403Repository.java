package example.repo;

import example.model.Customer1403;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1403Repository extends CrudRepository<Customer1403, Long> {

	List<Customer1403> findByLastName(String lastName);
}
