package example.repo;

import example.model.Customer1217;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1217Repository extends CrudRepository<Customer1217, Long> {

	List<Customer1217> findByLastName(String lastName);
}
