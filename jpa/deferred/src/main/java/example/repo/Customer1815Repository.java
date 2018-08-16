package example.repo;

import example.model.Customer1815;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1815Repository extends CrudRepository<Customer1815, Long> {

	List<Customer1815> findByLastName(String lastName);
}
