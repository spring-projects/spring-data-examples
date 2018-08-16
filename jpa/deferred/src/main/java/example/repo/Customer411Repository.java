package example.repo;

import example.model.Customer411;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer411Repository extends CrudRepository<Customer411, Long> {

	List<Customer411> findByLastName(String lastName);
}
