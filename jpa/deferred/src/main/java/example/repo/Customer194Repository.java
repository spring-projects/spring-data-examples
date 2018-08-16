package example.repo;

import example.model.Customer194;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer194Repository extends CrudRepository<Customer194, Long> {

	List<Customer194> findByLastName(String lastName);
}
