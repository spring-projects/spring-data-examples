package example.repo;

import example.model.Customer1398;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1398Repository extends CrudRepository<Customer1398, Long> {

	List<Customer1398> findByLastName(String lastName);
}
