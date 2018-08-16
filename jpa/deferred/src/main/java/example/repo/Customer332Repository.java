package example.repo;

import example.model.Customer332;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer332Repository extends CrudRepository<Customer332, Long> {

	List<Customer332> findByLastName(String lastName);
}
