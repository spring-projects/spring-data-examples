package example.repo;

import example.model.Customer945;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer945Repository extends CrudRepository<Customer945, Long> {

	List<Customer945> findByLastName(String lastName);
}
