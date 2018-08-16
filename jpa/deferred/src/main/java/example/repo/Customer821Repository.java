package example.repo;

import example.model.Customer821;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer821Repository extends CrudRepository<Customer821, Long> {

	List<Customer821> findByLastName(String lastName);
}
