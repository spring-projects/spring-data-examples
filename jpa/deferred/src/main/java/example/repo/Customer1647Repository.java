package example.repo;

import example.model.Customer1647;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1647Repository extends CrudRepository<Customer1647, Long> {

	List<Customer1647> findByLastName(String lastName);
}
