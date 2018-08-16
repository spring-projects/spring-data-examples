package example.repo;

import example.model.Customer1582;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1582Repository extends CrudRepository<Customer1582, Long> {

	List<Customer1582> findByLastName(String lastName);
}
