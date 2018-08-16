package example.repo;

import example.model.Customer299;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer299Repository extends CrudRepository<Customer299, Long> {

	List<Customer299> findByLastName(String lastName);
}
