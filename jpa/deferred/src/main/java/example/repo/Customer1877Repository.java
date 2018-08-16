package example.repo;

import example.model.Customer1877;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1877Repository extends CrudRepository<Customer1877, Long> {

	List<Customer1877> findByLastName(String lastName);
}
