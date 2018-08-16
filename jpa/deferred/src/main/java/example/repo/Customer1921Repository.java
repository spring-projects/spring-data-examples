package example.repo;

import example.model.Customer1921;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1921Repository extends CrudRepository<Customer1921, Long> {

	List<Customer1921> findByLastName(String lastName);
}
