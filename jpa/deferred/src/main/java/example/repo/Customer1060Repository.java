package example.repo;

import example.model.Customer1060;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1060Repository extends CrudRepository<Customer1060, Long> {

	List<Customer1060> findByLastName(String lastName);
}
