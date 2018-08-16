package example.repo;

import example.model.Customer103;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer103Repository extends CrudRepository<Customer103, Long> {

	List<Customer103> findByLastName(String lastName);
}
