package example.repo;

import example.model.Customer1734;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1734Repository extends CrudRepository<Customer1734, Long> {

	List<Customer1734> findByLastName(String lastName);
}
