package example.repo;

import example.model.Customer1603;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1603Repository extends CrudRepository<Customer1603, Long> {

	List<Customer1603> findByLastName(String lastName);
}
