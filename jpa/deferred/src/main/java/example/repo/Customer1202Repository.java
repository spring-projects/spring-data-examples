package example.repo;

import example.model.Customer1202;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1202Repository extends CrudRepository<Customer1202, Long> {

	List<Customer1202> findByLastName(String lastName);
}
