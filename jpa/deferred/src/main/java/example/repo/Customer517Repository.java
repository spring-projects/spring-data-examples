package example.repo;

import example.model.Customer517;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer517Repository extends CrudRepository<Customer517, Long> {

	List<Customer517> findByLastName(String lastName);
}
