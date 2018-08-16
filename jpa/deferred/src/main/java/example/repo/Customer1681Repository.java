package example.repo;

import example.model.Customer1681;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1681Repository extends CrudRepository<Customer1681, Long> {

	List<Customer1681> findByLastName(String lastName);
}
