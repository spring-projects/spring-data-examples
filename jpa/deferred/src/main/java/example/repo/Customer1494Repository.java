package example.repo;

import example.model.Customer1494;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1494Repository extends CrudRepository<Customer1494, Long> {

	List<Customer1494> findByLastName(String lastName);
}
