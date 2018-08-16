package example.repo;

import example.model.Customer1248;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1248Repository extends CrudRepository<Customer1248, Long> {

	List<Customer1248> findByLastName(String lastName);
}
