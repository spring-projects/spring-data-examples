package example.repo;

import example.model.Customer1917;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1917Repository extends CrudRepository<Customer1917, Long> {

	List<Customer1917> findByLastName(String lastName);
}
