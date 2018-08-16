package example.repo;

import example.model.Customer1536;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1536Repository extends CrudRepository<Customer1536, Long> {

	List<Customer1536> findByLastName(String lastName);
}
