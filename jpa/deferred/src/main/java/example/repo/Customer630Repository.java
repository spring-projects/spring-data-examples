package example.repo;

import example.model.Customer630;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer630Repository extends CrudRepository<Customer630, Long> {

	List<Customer630> findByLastName(String lastName);
}
