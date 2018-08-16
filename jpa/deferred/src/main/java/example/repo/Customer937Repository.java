package example.repo;

import example.model.Customer937;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer937Repository extends CrudRepository<Customer937, Long> {

	List<Customer937> findByLastName(String lastName);
}
