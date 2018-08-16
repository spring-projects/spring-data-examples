package example.repo;

import example.model.Customer1339;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1339Repository extends CrudRepository<Customer1339, Long> {

	List<Customer1339> findByLastName(String lastName);
}
