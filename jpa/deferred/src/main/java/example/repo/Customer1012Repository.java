package example.repo;

import example.model.Customer1012;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1012Repository extends CrudRepository<Customer1012, Long> {

	List<Customer1012> findByLastName(String lastName);
}
