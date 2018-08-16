package example.repo;

import example.model.Customer1477;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1477Repository extends CrudRepository<Customer1477, Long> {

	List<Customer1477> findByLastName(String lastName);
}
