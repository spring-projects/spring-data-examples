package example.repo;

import example.model.Customer1696;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1696Repository extends CrudRepository<Customer1696, Long> {

	List<Customer1696> findByLastName(String lastName);
}
