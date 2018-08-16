package example.repo;

import example.model.Customer1181;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1181Repository extends CrudRepository<Customer1181, Long> {

	List<Customer1181> findByLastName(String lastName);
}
