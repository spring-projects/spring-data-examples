package example.repo;

import example.model.Customer1789;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1789Repository extends CrudRepository<Customer1789, Long> {

	List<Customer1789> findByLastName(String lastName);
}
