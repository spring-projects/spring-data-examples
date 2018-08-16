package example.repo;

import example.model.Customer1170;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1170Repository extends CrudRepository<Customer1170, Long> {

	List<Customer1170> findByLastName(String lastName);
}
