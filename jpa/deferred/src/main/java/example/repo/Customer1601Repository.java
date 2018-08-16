package example.repo;

import example.model.Customer1601;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1601Repository extends CrudRepository<Customer1601, Long> {

	List<Customer1601> findByLastName(String lastName);
}
