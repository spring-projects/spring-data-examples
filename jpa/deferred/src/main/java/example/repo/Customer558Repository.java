package example.repo;

import example.model.Customer558;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer558Repository extends CrudRepository<Customer558, Long> {

	List<Customer558> findByLastName(String lastName);
}
