package example.repo;

import example.model.Customer34;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer34Repository extends CrudRepository<Customer34, Long> {

	List<Customer34> findByLastName(String lastName);
}
