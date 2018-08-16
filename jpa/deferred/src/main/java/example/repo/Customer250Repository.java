package example.repo;

import example.model.Customer250;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer250Repository extends CrudRepository<Customer250, Long> {

	List<Customer250> findByLastName(String lastName);
}
