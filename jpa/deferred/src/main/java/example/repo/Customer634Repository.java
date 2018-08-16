package example.repo;

import example.model.Customer634;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer634Repository extends CrudRepository<Customer634, Long> {

	List<Customer634> findByLastName(String lastName);
}
