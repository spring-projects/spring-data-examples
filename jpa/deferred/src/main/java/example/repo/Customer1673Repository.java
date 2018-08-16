package example.repo;

import example.model.Customer1673;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1673Repository extends CrudRepository<Customer1673, Long> {

	List<Customer1673> findByLastName(String lastName);
}
