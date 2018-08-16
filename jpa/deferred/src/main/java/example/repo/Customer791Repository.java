package example.repo;

import example.model.Customer791;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer791Repository extends CrudRepository<Customer791, Long> {

	List<Customer791> findByLastName(String lastName);
}
