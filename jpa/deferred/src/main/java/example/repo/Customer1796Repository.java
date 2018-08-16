package example.repo;

import example.model.Customer1796;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1796Repository extends CrudRepository<Customer1796, Long> {

	List<Customer1796> findByLastName(String lastName);
}
