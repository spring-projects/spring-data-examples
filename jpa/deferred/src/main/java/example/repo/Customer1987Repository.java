package example.repo;

import example.model.Customer1987;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1987Repository extends CrudRepository<Customer1987, Long> {

	List<Customer1987> findByLastName(String lastName);
}
