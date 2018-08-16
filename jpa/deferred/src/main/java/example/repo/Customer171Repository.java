package example.repo;

import example.model.Customer171;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer171Repository extends CrudRepository<Customer171, Long> {

	List<Customer171> findByLastName(String lastName);
}
