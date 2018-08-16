package example.repo;

import example.model.Customer1224;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1224Repository extends CrudRepository<Customer1224, Long> {

	List<Customer1224> findByLastName(String lastName);
}
