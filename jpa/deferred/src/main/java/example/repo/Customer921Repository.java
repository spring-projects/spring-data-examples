package example.repo;

import example.model.Customer921;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer921Repository extends CrudRepository<Customer921, Long> {

	List<Customer921> findByLastName(String lastName);
}
