package example.repo;

import example.model.Customer773;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer773Repository extends CrudRepository<Customer773, Long> {

	List<Customer773> findByLastName(String lastName);
}
