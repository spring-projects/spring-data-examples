package example.repo;

import example.model.Customer1757;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1757Repository extends CrudRepository<Customer1757, Long> {

	List<Customer1757> findByLastName(String lastName);
}
