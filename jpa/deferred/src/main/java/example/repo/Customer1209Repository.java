package example.repo;

import example.model.Customer1209;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1209Repository extends CrudRepository<Customer1209, Long> {

	List<Customer1209> findByLastName(String lastName);
}
