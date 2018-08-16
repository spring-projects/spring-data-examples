package example.repo;

import example.model.Customer1832;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1832Repository extends CrudRepository<Customer1832, Long> {

	List<Customer1832> findByLastName(String lastName);
}
