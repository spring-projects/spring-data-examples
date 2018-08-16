package example.repo;

import example.model.Customer1969;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1969Repository extends CrudRepository<Customer1969, Long> {

	List<Customer1969> findByLastName(String lastName);
}
