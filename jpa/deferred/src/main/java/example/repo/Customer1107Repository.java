package example.repo;

import example.model.Customer1107;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1107Repository extends CrudRepository<Customer1107, Long> {

	List<Customer1107> findByLastName(String lastName);
}
