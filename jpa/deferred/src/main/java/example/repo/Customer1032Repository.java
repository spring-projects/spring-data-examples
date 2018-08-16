package example.repo;

import example.model.Customer1032;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1032Repository extends CrudRepository<Customer1032, Long> {

	List<Customer1032> findByLastName(String lastName);
}
