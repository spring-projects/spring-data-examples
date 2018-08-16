package example.repo;

import example.model.Customer1698;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1698Repository extends CrudRepository<Customer1698, Long> {

	List<Customer1698> findByLastName(String lastName);
}
