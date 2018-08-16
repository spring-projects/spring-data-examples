package example.repo;

import example.model.Customer173;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer173Repository extends CrudRepository<Customer173, Long> {

	List<Customer173> findByLastName(String lastName);
}
