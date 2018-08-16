package example.repo;

import example.model.Customer561;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer561Repository extends CrudRepository<Customer561, Long> {

	List<Customer561> findByLastName(String lastName);
}
