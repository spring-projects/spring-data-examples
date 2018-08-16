package example.repo;

import example.model.Customer1515;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1515Repository extends CrudRepository<Customer1515, Long> {

	List<Customer1515> findByLastName(String lastName);
}
