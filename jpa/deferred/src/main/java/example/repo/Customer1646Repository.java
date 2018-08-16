package example.repo;

import example.model.Customer1646;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1646Repository extends CrudRepository<Customer1646, Long> {

	List<Customer1646> findByLastName(String lastName);
}
