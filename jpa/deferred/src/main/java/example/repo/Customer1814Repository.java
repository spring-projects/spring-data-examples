package example.repo;

import example.model.Customer1814;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1814Repository extends CrudRepository<Customer1814, Long> {

	List<Customer1814> findByLastName(String lastName);
}
