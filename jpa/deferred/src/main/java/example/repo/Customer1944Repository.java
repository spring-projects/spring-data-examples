package example.repo;

import example.model.Customer1944;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1944Repository extends CrudRepository<Customer1944, Long> {

	List<Customer1944> findByLastName(String lastName);
}
