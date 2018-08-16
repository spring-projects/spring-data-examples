package example.repo;

import example.model.Customer290;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer290Repository extends CrudRepository<Customer290, Long> {

	List<Customer290> findByLastName(String lastName);
}
