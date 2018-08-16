package example.repo;

import example.model.Customer1863;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1863Repository extends CrudRepository<Customer1863, Long> {

	List<Customer1863> findByLastName(String lastName);
}
