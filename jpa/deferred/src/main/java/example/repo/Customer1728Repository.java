package example.repo;

import example.model.Customer1728;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1728Repository extends CrudRepository<Customer1728, Long> {

	List<Customer1728> findByLastName(String lastName);
}
