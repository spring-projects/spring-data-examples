package example.repo;

import example.model.Customer50;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer50Repository extends CrudRepository<Customer50, Long> {

	List<Customer50> findByLastName(String lastName);
}
