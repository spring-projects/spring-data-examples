package example.repo;

import example.model.Customer355;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer355Repository extends CrudRepository<Customer355, Long> {

	List<Customer355> findByLastName(String lastName);
}
