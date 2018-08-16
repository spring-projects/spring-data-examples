package example.repo;

import example.model.Customer1824;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1824Repository extends CrudRepository<Customer1824, Long> {

	List<Customer1824> findByLastName(String lastName);
}
