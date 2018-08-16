package example.repo;

import example.model.Customer894;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer894Repository extends CrudRepository<Customer894, Long> {

	List<Customer894> findByLastName(String lastName);
}
