package example.repo;

import example.model.Customer683;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer683Repository extends CrudRepository<Customer683, Long> {

	List<Customer683> findByLastName(String lastName);
}
