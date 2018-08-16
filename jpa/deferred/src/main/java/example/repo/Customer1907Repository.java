package example.repo;

import example.model.Customer1907;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1907Repository extends CrudRepository<Customer1907, Long> {

	List<Customer1907> findByLastName(String lastName);
}
