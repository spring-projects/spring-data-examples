package example.repo;

import example.model.Customer222;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer222Repository extends CrudRepository<Customer222, Long> {

	List<Customer222> findByLastName(String lastName);
}
