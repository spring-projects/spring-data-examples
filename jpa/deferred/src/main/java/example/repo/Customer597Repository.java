package example.repo;

import example.model.Customer597;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer597Repository extends CrudRepository<Customer597, Long> {

	List<Customer597> findByLastName(String lastName);
}
