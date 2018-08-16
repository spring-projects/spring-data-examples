package example.repo;

import example.model.Customer803;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer803Repository extends CrudRepository<Customer803, Long> {

	List<Customer803> findByLastName(String lastName);
}
