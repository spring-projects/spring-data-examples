package example.repo;

import example.model.Customer435;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer435Repository extends CrudRepository<Customer435, Long> {

	List<Customer435> findByLastName(String lastName);
}
