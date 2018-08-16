package example.repo;

import example.model.Customer1158;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1158Repository extends CrudRepository<Customer1158, Long> {

	List<Customer1158> findByLastName(String lastName);
}
