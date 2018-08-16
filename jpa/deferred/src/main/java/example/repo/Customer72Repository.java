package example.repo;

import example.model.Customer72;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer72Repository extends CrudRepository<Customer72, Long> {

	List<Customer72> findByLastName(String lastName);
}
