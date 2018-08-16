package example.repo;

import example.model.Customer1662;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1662Repository extends CrudRepository<Customer1662, Long> {

	List<Customer1662> findByLastName(String lastName);
}
