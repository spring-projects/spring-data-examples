package example.repo;

import example.model.Customer1854;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1854Repository extends CrudRepository<Customer1854, Long> {

	List<Customer1854> findByLastName(String lastName);
}
