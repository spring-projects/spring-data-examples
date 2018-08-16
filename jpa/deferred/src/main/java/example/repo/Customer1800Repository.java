package example.repo;

import example.model.Customer1800;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1800Repository extends CrudRepository<Customer1800, Long> {

	List<Customer1800> findByLastName(String lastName);
}
