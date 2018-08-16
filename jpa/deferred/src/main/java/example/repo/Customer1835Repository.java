package example.repo;

import example.model.Customer1835;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1835Repository extends CrudRepository<Customer1835, Long> {

	List<Customer1835> findByLastName(String lastName);
}
