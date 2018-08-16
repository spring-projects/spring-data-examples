package example.repo;

import example.model.Customer1507;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1507Repository extends CrudRepository<Customer1507, Long> {

	List<Customer1507> findByLastName(String lastName);
}
