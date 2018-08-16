package example.repo;

import example.model.Customer643;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer643Repository extends CrudRepository<Customer643, Long> {

	List<Customer643> findByLastName(String lastName);
}
