package example.repo;

import example.model.Customer753;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer753Repository extends CrudRepository<Customer753, Long> {

	List<Customer753> findByLastName(String lastName);
}
