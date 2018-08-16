package example.repo;

import example.model.Customer929;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer929Repository extends CrudRepository<Customer929, Long> {

	List<Customer929> findByLastName(String lastName);
}
