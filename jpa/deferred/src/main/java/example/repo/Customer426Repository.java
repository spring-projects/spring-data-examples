package example.repo;

import example.model.Customer426;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer426Repository extends CrudRepository<Customer426, Long> {

	List<Customer426> findByLastName(String lastName);
}
