package example.repo;

import example.model.Customer1955;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1955Repository extends CrudRepository<Customer1955, Long> {

	List<Customer1955> findByLastName(String lastName);
}
