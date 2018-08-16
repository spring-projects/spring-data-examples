package example.repo;

import example.model.Customer1054;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1054Repository extends CrudRepository<Customer1054, Long> {

	List<Customer1054> findByLastName(String lastName);
}
