package example.repo;

import example.model.Customer1076;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1076Repository extends CrudRepository<Customer1076, Long> {

	List<Customer1076> findByLastName(String lastName);
}
