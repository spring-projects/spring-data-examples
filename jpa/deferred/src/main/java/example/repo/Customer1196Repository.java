package example.repo;

import example.model.Customer1196;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1196Repository extends CrudRepository<Customer1196, Long> {

	List<Customer1196> findByLastName(String lastName);
}
