package example.repo;

import example.model.Customer186;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer186Repository extends CrudRepository<Customer186, Long> {

	List<Customer186> findByLastName(String lastName);
}
