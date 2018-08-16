package example.repo;

import example.model.Customer1811;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1811Repository extends CrudRepository<Customer1811, Long> {

	List<Customer1811> findByLastName(String lastName);
}
