package example.repo;

import example.model.Customer813;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer813Repository extends CrudRepository<Customer813, Long> {

	List<Customer813> findByLastName(String lastName);
}
