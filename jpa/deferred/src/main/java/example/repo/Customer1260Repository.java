package example.repo;

import example.model.Customer1260;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1260Repository extends CrudRepository<Customer1260, Long> {

	List<Customer1260> findByLastName(String lastName);
}
