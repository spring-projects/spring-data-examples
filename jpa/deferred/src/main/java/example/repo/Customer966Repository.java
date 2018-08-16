package example.repo;

import example.model.Customer966;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer966Repository extends CrudRepository<Customer966, Long> {

	List<Customer966> findByLastName(String lastName);
}
