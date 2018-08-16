package example.repo;

import example.model.Customer1011;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1011Repository extends CrudRepository<Customer1011, Long> {

	List<Customer1011> findByLastName(String lastName);
}
