package example.repo;

import example.model.Customer74;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer74Repository extends CrudRepository<Customer74, Long> {

	List<Customer74> findByLastName(String lastName);
}
