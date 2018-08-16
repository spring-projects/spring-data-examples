package example.repo;

import example.model.Customer1977;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1977Repository extends CrudRepository<Customer1977, Long> {

	List<Customer1977> findByLastName(String lastName);
}
