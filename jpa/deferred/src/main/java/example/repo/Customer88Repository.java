package example.repo;

import example.model.Customer88;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer88Repository extends CrudRepository<Customer88, Long> {

	List<Customer88> findByLastName(String lastName);
}
