package example.repo;

import example.model.Customer1770;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1770Repository extends CrudRepository<Customer1770, Long> {

	List<Customer1770> findByLastName(String lastName);
}
