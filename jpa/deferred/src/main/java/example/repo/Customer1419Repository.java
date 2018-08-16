package example.repo;

import example.model.Customer1419;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1419Repository extends CrudRepository<Customer1419, Long> {

	List<Customer1419> findByLastName(String lastName);
}
