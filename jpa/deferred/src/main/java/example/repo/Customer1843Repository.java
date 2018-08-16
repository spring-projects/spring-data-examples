package example.repo;

import example.model.Customer1843;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1843Repository extends CrudRepository<Customer1843, Long> {

	List<Customer1843> findByLastName(String lastName);
}
