package example.repo;

import example.model.Customer873;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer873Repository extends CrudRepository<Customer873, Long> {

	List<Customer873> findByLastName(String lastName);
}
