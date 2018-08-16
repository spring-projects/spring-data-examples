package example.repo;

import example.model.Customer1078;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1078Repository extends CrudRepository<Customer1078, Long> {

	List<Customer1078> findByLastName(String lastName);
}
