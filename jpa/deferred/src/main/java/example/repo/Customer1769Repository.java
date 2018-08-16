package example.repo;

import example.model.Customer1769;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1769Repository extends CrudRepository<Customer1769, Long> {

	List<Customer1769> findByLastName(String lastName);
}
