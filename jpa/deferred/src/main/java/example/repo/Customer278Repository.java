package example.repo;

import example.model.Customer278;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer278Repository extends CrudRepository<Customer278, Long> {

	List<Customer278> findByLastName(String lastName);
}
