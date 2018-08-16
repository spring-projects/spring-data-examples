package example.repo;

import example.model.Customer1307;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1307Repository extends CrudRepository<Customer1307, Long> {

	List<Customer1307> findByLastName(String lastName);
}
