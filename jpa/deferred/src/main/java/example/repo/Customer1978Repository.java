package example.repo;

import example.model.Customer1978;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1978Repository extends CrudRepository<Customer1978, Long> {

	List<Customer1978> findByLastName(String lastName);
}
