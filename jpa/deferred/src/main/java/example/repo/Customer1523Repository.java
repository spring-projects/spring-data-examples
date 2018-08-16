package example.repo;

import example.model.Customer1523;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1523Repository extends CrudRepository<Customer1523, Long> {

	List<Customer1523> findByLastName(String lastName);
}
