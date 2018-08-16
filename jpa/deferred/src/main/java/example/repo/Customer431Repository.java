package example.repo;

import example.model.Customer431;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer431Repository extends CrudRepository<Customer431, Long> {

	List<Customer431> findByLastName(String lastName);
}
