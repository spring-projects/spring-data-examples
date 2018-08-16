package example.repo;

import example.model.Customer760;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer760Repository extends CrudRepository<Customer760, Long> {

	List<Customer760> findByLastName(String lastName);
}
