package example.repo;

import example.model.Customer90;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer90Repository extends CrudRepository<Customer90, Long> {

	List<Customer90> findByLastName(String lastName);
}
