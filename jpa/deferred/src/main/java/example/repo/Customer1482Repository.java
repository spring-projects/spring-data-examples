package example.repo;

import example.model.Customer1482;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1482Repository extends CrudRepository<Customer1482, Long> {

	List<Customer1482> findByLastName(String lastName);
}
