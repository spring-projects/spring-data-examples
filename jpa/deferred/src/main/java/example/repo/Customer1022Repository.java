package example.repo;

import example.model.Customer1022;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1022Repository extends CrudRepository<Customer1022, Long> {

	List<Customer1022> findByLastName(String lastName);
}
