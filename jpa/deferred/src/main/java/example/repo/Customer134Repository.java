package example.repo;

import example.model.Customer134;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer134Repository extends CrudRepository<Customer134, Long> {

	List<Customer134> findByLastName(String lastName);
}
