package example.repo;

import example.model.Customer59;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer59Repository extends CrudRepository<Customer59, Long> {

	List<Customer59> findByLastName(String lastName);
}
