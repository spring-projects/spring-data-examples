package example.repo;

import example.model.Customer1362;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1362Repository extends CrudRepository<Customer1362, Long> {

	List<Customer1362> findByLastName(String lastName);
}
