package example.repo;

import example.model.Customer749;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer749Repository extends CrudRepository<Customer749, Long> {

	List<Customer749> findByLastName(String lastName);
}
