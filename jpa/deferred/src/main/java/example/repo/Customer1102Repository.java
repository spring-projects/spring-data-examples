package example.repo;

import example.model.Customer1102;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1102Repository extends CrudRepository<Customer1102, Long> {

	List<Customer1102> findByLastName(String lastName);
}
