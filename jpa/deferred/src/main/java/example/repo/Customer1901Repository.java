package example.repo;

import example.model.Customer1901;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1901Repository extends CrudRepository<Customer1901, Long> {

	List<Customer1901> findByLastName(String lastName);
}
