package example.repo;

import example.model.Customer155;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer155Repository extends CrudRepository<Customer155, Long> {

	List<Customer155> findByLastName(String lastName);
}
