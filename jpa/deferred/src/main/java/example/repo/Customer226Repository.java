package example.repo;

import example.model.Customer226;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer226Repository extends CrudRepository<Customer226, Long> {

	List<Customer226> findByLastName(String lastName);
}
