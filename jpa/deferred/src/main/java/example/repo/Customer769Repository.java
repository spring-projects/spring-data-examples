package example.repo;

import example.model.Customer769;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer769Repository extends CrudRepository<Customer769, Long> {

	List<Customer769> findByLastName(String lastName);
}
