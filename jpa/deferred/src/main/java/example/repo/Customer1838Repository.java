package example.repo;

import example.model.Customer1838;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1838Repository extends CrudRepository<Customer1838, Long> {

	List<Customer1838> findByLastName(String lastName);
}
