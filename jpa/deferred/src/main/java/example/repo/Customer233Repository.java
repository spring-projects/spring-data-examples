package example.repo;

import example.model.Customer233;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer233Repository extends CrudRepository<Customer233, Long> {

	List<Customer233> findByLastName(String lastName);
}
