package example.repo;

import example.model.Customer737;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer737Repository extends CrudRepository<Customer737, Long> {

	List<Customer737> findByLastName(String lastName);
}
