package example.repo;

import example.model.Customer466;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer466Repository extends CrudRepository<Customer466, Long> {

	List<Customer466> findByLastName(String lastName);
}
