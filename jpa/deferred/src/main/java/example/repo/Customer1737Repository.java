package example.repo;

import example.model.Customer1737;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1737Repository extends CrudRepository<Customer1737, Long> {

	List<Customer1737> findByLastName(String lastName);
}
