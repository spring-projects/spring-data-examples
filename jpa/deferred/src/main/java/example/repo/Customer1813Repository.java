package example.repo;

import example.model.Customer1813;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1813Repository extends CrudRepository<Customer1813, Long> {

	List<Customer1813> findByLastName(String lastName);
}
