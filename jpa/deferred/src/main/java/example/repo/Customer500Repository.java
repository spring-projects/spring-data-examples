package example.repo;

import example.model.Customer500;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer500Repository extends CrudRepository<Customer500, Long> {

	List<Customer500> findByLastName(String lastName);
}
