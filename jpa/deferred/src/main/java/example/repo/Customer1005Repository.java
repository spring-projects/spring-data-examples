package example.repo;

import example.model.Customer1005;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1005Repository extends CrudRepository<Customer1005, Long> {

	List<Customer1005> findByLastName(String lastName);
}
