package example.repo;

import example.model.Customer1108;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1108Repository extends CrudRepository<Customer1108, Long> {

	List<Customer1108> findByLastName(String lastName);
}
