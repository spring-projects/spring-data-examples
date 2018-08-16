package example.repo;

import example.model.Customer1874;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1874Repository extends CrudRepository<Customer1874, Long> {

	List<Customer1874> findByLastName(String lastName);
}
