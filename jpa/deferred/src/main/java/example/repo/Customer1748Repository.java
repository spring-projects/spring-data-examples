package example.repo;

import example.model.Customer1748;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1748Repository extends CrudRepository<Customer1748, Long> {

	List<Customer1748> findByLastName(String lastName);
}
