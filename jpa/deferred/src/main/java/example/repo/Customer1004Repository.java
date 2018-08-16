package example.repo;

import example.model.Customer1004;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1004Repository extends CrudRepository<Customer1004, Long> {

	List<Customer1004> findByLastName(String lastName);
}
