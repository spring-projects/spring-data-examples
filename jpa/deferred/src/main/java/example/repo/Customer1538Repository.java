package example.repo;

import example.model.Customer1538;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1538Repository extends CrudRepository<Customer1538, Long> {

	List<Customer1538> findByLastName(String lastName);
}
