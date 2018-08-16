package example.repo;

import example.model.Customer1859;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1859Repository extends CrudRepository<Customer1859, Long> {

	List<Customer1859> findByLastName(String lastName);
}
