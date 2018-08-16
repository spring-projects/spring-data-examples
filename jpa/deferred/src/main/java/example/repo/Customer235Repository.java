package example.repo;

import example.model.Customer235;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer235Repository extends CrudRepository<Customer235, Long> {

	List<Customer235> findByLastName(String lastName);
}
