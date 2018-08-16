package example.repo;

import example.model.Customer1360;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1360Repository extends CrudRepository<Customer1360, Long> {

	List<Customer1360> findByLastName(String lastName);
}
