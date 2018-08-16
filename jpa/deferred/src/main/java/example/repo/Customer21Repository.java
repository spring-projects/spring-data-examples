package example.repo;

import example.model.Customer21;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer21Repository extends CrudRepository<Customer21, Long> {

	List<Customer21> findByLastName(String lastName);
}
