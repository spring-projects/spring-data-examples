package example.repo;

import example.model.Customer802;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer802Repository extends CrudRepository<Customer802, Long> {

	List<Customer802> findByLastName(String lastName);
}
