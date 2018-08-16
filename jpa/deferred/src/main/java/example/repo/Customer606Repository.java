package example.repo;

import example.model.Customer606;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer606Repository extends CrudRepository<Customer606, Long> {

	List<Customer606> findByLastName(String lastName);
}
