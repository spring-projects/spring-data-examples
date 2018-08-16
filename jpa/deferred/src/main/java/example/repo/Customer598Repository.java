package example.repo;

import example.model.Customer598;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer598Repository extends CrudRepository<Customer598, Long> {

	List<Customer598> findByLastName(String lastName);
}
