package example.repo;

import example.model.Customer443;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer443Repository extends CrudRepository<Customer443, Long> {

	List<Customer443> findByLastName(String lastName);
}
