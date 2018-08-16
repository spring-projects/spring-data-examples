package example.repo;

import example.model.Customer1272;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1272Repository extends CrudRepository<Customer1272, Long> {

	List<Customer1272> findByLastName(String lastName);
}
