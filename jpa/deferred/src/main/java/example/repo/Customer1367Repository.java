package example.repo;

import example.model.Customer1367;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1367Repository extends CrudRepository<Customer1367, Long> {

	List<Customer1367> findByLastName(String lastName);
}
