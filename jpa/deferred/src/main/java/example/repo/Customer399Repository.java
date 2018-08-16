package example.repo;

import example.model.Customer399;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer399Repository extends CrudRepository<Customer399, Long> {

	List<Customer399> findByLastName(String lastName);
}
