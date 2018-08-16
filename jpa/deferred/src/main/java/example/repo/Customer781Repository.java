package example.repo;

import example.model.Customer781;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer781Repository extends CrudRepository<Customer781, Long> {

	List<Customer781> findByLastName(String lastName);
}
