package example.repo;

import example.model.Customer994;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer994Repository extends CrudRepository<Customer994, Long> {

	List<Customer994> findByLastName(String lastName);
}
