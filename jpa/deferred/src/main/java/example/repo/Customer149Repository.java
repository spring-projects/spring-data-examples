package example.repo;

import example.model.Customer149;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer149Repository extends CrudRepository<Customer149, Long> {

	List<Customer149> findByLastName(String lastName);
}
