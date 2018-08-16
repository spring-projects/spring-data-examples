package example.repo;

import example.model.Customer461;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer461Repository extends CrudRepository<Customer461, Long> {

	List<Customer461> findByLastName(String lastName);
}
