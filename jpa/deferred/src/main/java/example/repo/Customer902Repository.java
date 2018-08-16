package example.repo;

import example.model.Customer902;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer902Repository extends CrudRepository<Customer902, Long> {

	List<Customer902> findByLastName(String lastName);
}
