package example.repo;

import example.model.Customer709;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer709Repository extends CrudRepository<Customer709, Long> {

	List<Customer709> findByLastName(String lastName);
}
