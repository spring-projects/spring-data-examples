package example.repo;

import example.model.Customer1558;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1558Repository extends CrudRepository<Customer1558, Long> {

	List<Customer1558> findByLastName(String lastName);
}
