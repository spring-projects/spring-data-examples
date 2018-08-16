package example.repo;

import example.model.Customer1324;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1324Repository extends CrudRepository<Customer1324, Long> {

	List<Customer1324> findByLastName(String lastName);
}
