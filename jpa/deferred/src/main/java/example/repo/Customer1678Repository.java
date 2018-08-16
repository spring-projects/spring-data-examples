package example.repo;

import example.model.Customer1678;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1678Repository extends CrudRepository<Customer1678, Long> {

	List<Customer1678> findByLastName(String lastName);
}
