package example.repo;

import example.model.Customer1390;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1390Repository extends CrudRepository<Customer1390, Long> {

	List<Customer1390> findByLastName(String lastName);
}
