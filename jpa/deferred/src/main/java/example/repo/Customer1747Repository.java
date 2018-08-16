package example.repo;

import example.model.Customer1747;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1747Repository extends CrudRepository<Customer1747, Long> {

	List<Customer1747> findByLastName(String lastName);
}
