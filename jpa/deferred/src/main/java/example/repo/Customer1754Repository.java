package example.repo;

import example.model.Customer1754;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1754Repository extends CrudRepository<Customer1754, Long> {

	List<Customer1754> findByLastName(String lastName);
}
