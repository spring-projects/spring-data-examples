package example.repo;

import example.model.Customer856;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer856Repository extends CrudRepository<Customer856, Long> {

	List<Customer856> findByLastName(String lastName);
}
