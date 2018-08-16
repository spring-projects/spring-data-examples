package example.repo;

import example.model.Customer408;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer408Repository extends CrudRepository<Customer408, Long> {

	List<Customer408> findByLastName(String lastName);
}
