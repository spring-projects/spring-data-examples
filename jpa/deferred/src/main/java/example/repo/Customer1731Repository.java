package example.repo;

import example.model.Customer1731;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1731Repository extends CrudRepository<Customer1731, Long> {

	List<Customer1731> findByLastName(String lastName);
}
