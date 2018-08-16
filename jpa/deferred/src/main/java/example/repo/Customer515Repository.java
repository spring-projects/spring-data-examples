package example.repo;

import example.model.Customer515;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer515Repository extends CrudRepository<Customer515, Long> {

	List<Customer515> findByLastName(String lastName);
}
