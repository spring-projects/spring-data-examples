package example.repo;

import example.model.Customer1296;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1296Repository extends CrudRepository<Customer1296, Long> {

	List<Customer1296> findByLastName(String lastName);
}
