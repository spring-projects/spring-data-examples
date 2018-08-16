package example.repo;

import example.model.Customer446;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer446Repository extends CrudRepository<Customer446, Long> {

	List<Customer446> findByLastName(String lastName);
}
