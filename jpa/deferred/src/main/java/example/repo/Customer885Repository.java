package example.repo;

import example.model.Customer885;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer885Repository extends CrudRepository<Customer885, Long> {

	List<Customer885> findByLastName(String lastName);
}
