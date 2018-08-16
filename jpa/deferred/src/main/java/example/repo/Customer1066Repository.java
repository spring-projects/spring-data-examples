package example.repo;

import example.model.Customer1066;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1066Repository extends CrudRepository<Customer1066, Long> {

	List<Customer1066> findByLastName(String lastName);
}
