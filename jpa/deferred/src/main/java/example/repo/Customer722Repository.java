package example.repo;

import example.model.Customer722;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer722Repository extends CrudRepository<Customer722, Long> {

	List<Customer722> findByLastName(String lastName);
}
