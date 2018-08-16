package example.repo;

import example.model.Customer1643;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1643Repository extends CrudRepository<Customer1643, Long> {

	List<Customer1643> findByLastName(String lastName);
}
