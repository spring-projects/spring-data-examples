package example.repo;

import example.model.Customer1139;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1139Repository extends CrudRepository<Customer1139, Long> {

	List<Customer1139> findByLastName(String lastName);
}
