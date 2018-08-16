package example.repo;

import example.model.Customer1574;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1574Repository extends CrudRepository<Customer1574, Long> {

	List<Customer1574> findByLastName(String lastName);
}
