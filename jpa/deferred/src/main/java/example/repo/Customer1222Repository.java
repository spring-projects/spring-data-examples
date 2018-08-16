package example.repo;

import example.model.Customer1222;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1222Repository extends CrudRepository<Customer1222, Long> {

	List<Customer1222> findByLastName(String lastName);
}
