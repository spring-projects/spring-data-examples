package example.repo;

import example.model.Customer616;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer616Repository extends CrudRepository<Customer616, Long> {

	List<Customer616> findByLastName(String lastName);
}
