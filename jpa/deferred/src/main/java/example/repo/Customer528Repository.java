package example.repo;

import example.model.Customer528;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer528Repository extends CrudRepository<Customer528, Long> {

	List<Customer528> findByLastName(String lastName);
}
