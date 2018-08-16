package example.repo;

import example.model.Customer1713;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1713Repository extends CrudRepository<Customer1713, Long> {

	List<Customer1713> findByLastName(String lastName);
}
