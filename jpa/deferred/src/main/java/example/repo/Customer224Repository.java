package example.repo;

import example.model.Customer224;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer224Repository extends CrudRepository<Customer224, Long> {

	List<Customer224> findByLastName(String lastName);
}
