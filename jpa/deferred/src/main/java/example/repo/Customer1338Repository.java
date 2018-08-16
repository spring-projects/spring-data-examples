package example.repo;

import example.model.Customer1338;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1338Repository extends CrudRepository<Customer1338, Long> {

	List<Customer1338> findByLastName(String lastName);
}
