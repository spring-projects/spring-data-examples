package example.repo;

import example.model.Customer677;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer677Repository extends CrudRepository<Customer677, Long> {

	List<Customer677> findByLastName(String lastName);
}
