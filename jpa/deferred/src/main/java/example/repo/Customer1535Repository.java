package example.repo;

import example.model.Customer1535;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1535Repository extends CrudRepository<Customer1535, Long> {

	List<Customer1535> findByLastName(String lastName);
}
