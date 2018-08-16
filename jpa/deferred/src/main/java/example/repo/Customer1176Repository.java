package example.repo;

import example.model.Customer1176;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1176Repository extends CrudRepository<Customer1176, Long> {

	List<Customer1176> findByLastName(String lastName);
}
