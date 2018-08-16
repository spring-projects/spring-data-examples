package example.repo;

import example.model.Customer1551;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1551Repository extends CrudRepository<Customer1551, Long> {

	List<Customer1551> findByLastName(String lastName);
}
