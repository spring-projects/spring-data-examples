package example.repo;

import example.model.Customer475;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer475Repository extends CrudRepository<Customer475, Long> {

	List<Customer475> findByLastName(String lastName);
}
