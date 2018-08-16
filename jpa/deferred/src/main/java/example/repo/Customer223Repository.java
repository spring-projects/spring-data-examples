package example.repo;

import example.model.Customer223;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer223Repository extends CrudRepository<Customer223, Long> {

	List<Customer223> findByLastName(String lastName);
}
