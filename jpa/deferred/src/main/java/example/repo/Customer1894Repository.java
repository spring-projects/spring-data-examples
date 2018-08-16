package example.repo;

import example.model.Customer1894;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1894Repository extends CrudRepository<Customer1894, Long> {

	List<Customer1894> findByLastName(String lastName);
}
