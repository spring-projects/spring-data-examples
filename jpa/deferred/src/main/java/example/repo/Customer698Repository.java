package example.repo;

import example.model.Customer698;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer698Repository extends CrudRepository<Customer698, Long> {

	List<Customer698> findByLastName(String lastName);
}
