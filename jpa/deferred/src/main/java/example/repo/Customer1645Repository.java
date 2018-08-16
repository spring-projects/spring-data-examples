package example.repo;

import example.model.Customer1645;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1645Repository extends CrudRepository<Customer1645, Long> {

	List<Customer1645> findByLastName(String lastName);
}
