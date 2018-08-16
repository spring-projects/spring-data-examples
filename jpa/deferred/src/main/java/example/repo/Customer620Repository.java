package example.repo;

import example.model.Customer620;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer620Repository extends CrudRepository<Customer620, Long> {

	List<Customer620> findByLastName(String lastName);
}
