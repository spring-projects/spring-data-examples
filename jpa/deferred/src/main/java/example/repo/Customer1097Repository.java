package example.repo;

import example.model.Customer1097;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1097Repository extends CrudRepository<Customer1097, Long> {

	List<Customer1097> findByLastName(String lastName);
}
