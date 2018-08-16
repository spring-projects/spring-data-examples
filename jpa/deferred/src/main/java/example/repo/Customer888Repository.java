package example.repo;

import example.model.Customer888;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer888Repository extends CrudRepository<Customer888, Long> {

	List<Customer888> findByLastName(String lastName);
}
