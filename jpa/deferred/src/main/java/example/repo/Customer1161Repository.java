package example.repo;

import example.model.Customer1161;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1161Repository extends CrudRepository<Customer1161, Long> {

	List<Customer1161> findByLastName(String lastName);
}
