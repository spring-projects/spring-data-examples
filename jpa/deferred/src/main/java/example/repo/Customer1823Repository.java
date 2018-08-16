package example.repo;

import example.model.Customer1823;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1823Repository extends CrudRepository<Customer1823, Long> {

	List<Customer1823> findByLastName(String lastName);
}
