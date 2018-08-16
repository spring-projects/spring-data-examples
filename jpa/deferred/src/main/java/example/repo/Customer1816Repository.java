package example.repo;

import example.model.Customer1816;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1816Repository extends CrudRepository<Customer1816, Long> {

	List<Customer1816> findByLastName(String lastName);
}
