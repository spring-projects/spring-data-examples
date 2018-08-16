package example.repo;

import example.model.Customer1410;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1410Repository extends CrudRepository<Customer1410, Long> {

	List<Customer1410> findByLastName(String lastName);
}
