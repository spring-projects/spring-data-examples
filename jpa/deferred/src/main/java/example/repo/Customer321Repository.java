package example.repo;

import example.model.Customer321;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer321Repository extends CrudRepository<Customer321, Long> {

	List<Customer321> findByLastName(String lastName);
}
