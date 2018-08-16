package example.repo;

import example.model.Customer1957;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1957Repository extends CrudRepository<Customer1957, Long> {

	List<Customer1957> findByLastName(String lastName);
}
