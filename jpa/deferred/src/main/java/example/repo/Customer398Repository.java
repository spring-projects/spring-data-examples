package example.repo;

import example.model.Customer398;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer398Repository extends CrudRepository<Customer398, Long> {

	List<Customer398> findByLastName(String lastName);
}
