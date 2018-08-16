package example.repo;

import example.model.Customer280;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer280Repository extends CrudRepository<Customer280, Long> {

	List<Customer280> findByLastName(String lastName);
}
