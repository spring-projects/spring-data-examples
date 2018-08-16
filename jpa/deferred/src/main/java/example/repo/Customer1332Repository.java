package example.repo;

import example.model.Customer1332;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1332Repository extends CrudRepository<Customer1332, Long> {

	List<Customer1332> findByLastName(String lastName);
}
