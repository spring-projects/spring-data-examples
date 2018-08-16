package example.repo;

import example.model.Customer366;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer366Repository extends CrudRepository<Customer366, Long> {

	List<Customer366> findByLastName(String lastName);
}
