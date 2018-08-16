package example.repo;

import example.model.Customer991;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer991Repository extends CrudRepository<Customer991, Long> {

	List<Customer991> findByLastName(String lastName);
}
