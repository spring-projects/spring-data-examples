package example.repo;

import example.model.Customer799;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer799Repository extends CrudRepository<Customer799, Long> {

	List<Customer799> findByLastName(String lastName);
}
