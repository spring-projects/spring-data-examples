package example.repo;

import example.model.Customer1355;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1355Repository extends CrudRepository<Customer1355, Long> {

	List<Customer1355> findByLastName(String lastName);
}
