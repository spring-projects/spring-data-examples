package example.repo;

import example.model.Customer1042;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1042Repository extends CrudRepository<Customer1042, Long> {

	List<Customer1042> findByLastName(String lastName);
}
