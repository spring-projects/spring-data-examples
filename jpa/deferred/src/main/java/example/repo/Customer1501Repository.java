package example.repo;

import example.model.Customer1501;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1501Repository extends CrudRepository<Customer1501, Long> {

	List<Customer1501> findByLastName(String lastName);
}
