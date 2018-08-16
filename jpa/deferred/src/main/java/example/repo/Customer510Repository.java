package example.repo;

import example.model.Customer510;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer510Repository extends CrudRepository<Customer510, Long> {

	List<Customer510> findByLastName(String lastName);
}
