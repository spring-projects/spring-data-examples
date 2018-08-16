package example.repo;

import example.model.Customer549;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer549Repository extends CrudRepository<Customer549, Long> {

	List<Customer549> findByLastName(String lastName);
}
