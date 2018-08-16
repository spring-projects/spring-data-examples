package example.repo;

import example.model.Customer1736;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1736Repository extends CrudRepository<Customer1736, Long> {

	List<Customer1736> findByLastName(String lastName);
}
