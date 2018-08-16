package example.repo;

import example.model.Customer1727;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1727Repository extends CrudRepository<Customer1727, Long> {

	List<Customer1727> findByLastName(String lastName);
}
