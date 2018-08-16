package example.repo;

import example.model.Customer36;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer36Repository extends CrudRepository<Customer36, Long> {

	List<Customer36> findByLastName(String lastName);
}
