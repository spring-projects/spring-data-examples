package example.repo;

import example.model.Customer143;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer143Repository extends CrudRepository<Customer143, Long> {

	List<Customer143> findByLastName(String lastName);
}
