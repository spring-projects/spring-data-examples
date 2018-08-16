package example.repo;

import example.model.Customer394;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer394Repository extends CrudRepository<Customer394, Long> {

	List<Customer394> findByLastName(String lastName);
}
