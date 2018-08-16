package example.repo;

import example.model.Customer1992;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1992Repository extends CrudRepository<Customer1992, Long> {

	List<Customer1992> findByLastName(String lastName);
}
