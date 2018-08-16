package example.repo;

import example.model.Customer220;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer220Repository extends CrudRepository<Customer220, Long> {

	List<Customer220> findByLastName(String lastName);
}
