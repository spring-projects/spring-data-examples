package example.repo;

import example.model.Customer1865;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1865Repository extends CrudRepository<Customer1865, Long> {

	List<Customer1865> findByLastName(String lastName);
}
