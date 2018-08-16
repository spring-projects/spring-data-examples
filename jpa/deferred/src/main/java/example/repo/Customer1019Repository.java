package example.repo;

import example.model.Customer1019;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1019Repository extends CrudRepository<Customer1019, Long> {

	List<Customer1019> findByLastName(String lastName);
}
