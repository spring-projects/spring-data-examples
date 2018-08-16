package example.repo;

import example.model.Customer479;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer479Repository extends CrudRepository<Customer479, Long> {

	List<Customer479> findByLastName(String lastName);
}
