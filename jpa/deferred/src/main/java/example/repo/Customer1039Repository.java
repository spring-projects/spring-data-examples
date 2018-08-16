package example.repo;

import example.model.Customer1039;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1039Repository extends CrudRepository<Customer1039, Long> {

	List<Customer1039> findByLastName(String lastName);
}
