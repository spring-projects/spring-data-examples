package example.repo;

import example.model.Customer1975;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1975Repository extends CrudRepository<Customer1975, Long> {

	List<Customer1975> findByLastName(String lastName);
}
