package example.repo;

import example.model.Customer1762;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1762Repository extends CrudRepository<Customer1762, Long> {

	List<Customer1762> findByLastName(String lastName);
}
