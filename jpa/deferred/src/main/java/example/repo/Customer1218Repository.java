package example.repo;

import example.model.Customer1218;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1218Repository extends CrudRepository<Customer1218, Long> {

	List<Customer1218> findByLastName(String lastName);
}
