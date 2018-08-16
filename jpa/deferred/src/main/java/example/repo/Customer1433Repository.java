package example.repo;

import example.model.Customer1433;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1433Repository extends CrudRepository<Customer1433, Long> {

	List<Customer1433> findByLastName(String lastName);
}
