package example.repo;

import example.model.Customer181;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer181Repository extends CrudRepository<Customer181, Long> {

	List<Customer181> findByLastName(String lastName);
}
