package example.repo;

import example.model.Customer277;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer277Repository extends CrudRepository<Customer277, Long> {

	List<Customer277> findByLastName(String lastName);
}
