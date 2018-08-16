package example.repo;

import example.model.Customer1841;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1841Repository extends CrudRepository<Customer1841, Long> {

	List<Customer1841> findByLastName(String lastName);
}
