package example.repo;

import example.model.Customer1806;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1806Repository extends CrudRepository<Customer1806, Long> {

	List<Customer1806> findByLastName(String lastName);
}
