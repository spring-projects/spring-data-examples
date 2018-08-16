package example.repo;

import example.model.Customer69;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer69Repository extends CrudRepository<Customer69, Long> {

	List<Customer69> findByLastName(String lastName);
}
