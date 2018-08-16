package example.repo;

import example.model.Customer1799;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1799Repository extends CrudRepository<Customer1799, Long> {

	List<Customer1799> findByLastName(String lastName);
}
