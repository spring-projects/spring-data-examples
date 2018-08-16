package example.repo;

import example.model.Customer76;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer76Repository extends CrudRepository<Customer76, Long> {

	List<Customer76> findByLastName(String lastName);
}
