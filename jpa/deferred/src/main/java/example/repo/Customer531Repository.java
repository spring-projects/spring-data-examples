package example.repo;

import example.model.Customer531;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer531Repository extends CrudRepository<Customer531, Long> {

	List<Customer531> findByLastName(String lastName);
}
