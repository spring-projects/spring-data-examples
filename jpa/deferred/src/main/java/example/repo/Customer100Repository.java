package example.repo;

import example.model.Customer100;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer100Repository extends CrudRepository<Customer100, Long> {

	List<Customer100> findByLastName(String lastName);
}
