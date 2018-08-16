package example.repo;

import example.model.Customer131;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer131Repository extends CrudRepository<Customer131, Long> {

	List<Customer131> findByLastName(String lastName);
}
