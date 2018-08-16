package example.repo;

import example.model.Customer1809;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1809Repository extends CrudRepository<Customer1809, Long> {

	List<Customer1809> findByLastName(String lastName);
}
