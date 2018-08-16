package example.repo;

import example.model.Customer1377;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1377Repository extends CrudRepository<Customer1377, Long> {

	List<Customer1377> findByLastName(String lastName);
}
