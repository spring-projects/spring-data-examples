package example.repo;

import example.model.Customer543;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer543Repository extends CrudRepository<Customer543, Long> {

	List<Customer543> findByLastName(String lastName);
}
