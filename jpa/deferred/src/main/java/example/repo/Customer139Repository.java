package example.repo;

import example.model.Customer139;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer139Repository extends CrudRepository<Customer139, Long> {

	List<Customer139> findByLastName(String lastName);
}
