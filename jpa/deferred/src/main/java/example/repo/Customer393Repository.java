package example.repo;

import example.model.Customer393;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer393Repository extends CrudRepository<Customer393, Long> {

	List<Customer393> findByLastName(String lastName);
}
