package example.repo;

import example.model.Customer527;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer527Repository extends CrudRepository<Customer527, Long> {

	List<Customer527> findByLastName(String lastName);
}
