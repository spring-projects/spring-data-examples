package example.repo;

import example.model.Customer1822;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1822Repository extends CrudRepository<Customer1822, Long> {

	List<Customer1822> findByLastName(String lastName);
}
