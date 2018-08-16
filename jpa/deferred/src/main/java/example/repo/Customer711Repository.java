package example.repo;

import example.model.Customer711;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer711Repository extends CrudRepository<Customer711, Long> {

	List<Customer711> findByLastName(String lastName);
}
