package example.repo;

import example.model.Customer1215;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1215Repository extends CrudRepository<Customer1215, Long> {

	List<Customer1215> findByLastName(String lastName);
}
