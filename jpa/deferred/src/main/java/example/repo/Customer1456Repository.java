package example.repo;

import example.model.Customer1456;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1456Repository extends CrudRepository<Customer1456, Long> {

	List<Customer1456> findByLastName(String lastName);
}
