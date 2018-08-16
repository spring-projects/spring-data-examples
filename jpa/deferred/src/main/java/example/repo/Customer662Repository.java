package example.repo;

import example.model.Customer662;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer662Repository extends CrudRepository<Customer662, Long> {

	List<Customer662> findByLastName(String lastName);
}
