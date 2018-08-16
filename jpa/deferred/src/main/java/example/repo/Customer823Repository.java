package example.repo;

import example.model.Customer823;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer823Repository extends CrudRepository<Customer823, Long> {

	List<Customer823> findByLastName(String lastName);
}
