package example.repo;

import example.model.Customer447;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer447Repository extends CrudRepository<Customer447, Long> {

	List<Customer447> findByLastName(String lastName);
}
