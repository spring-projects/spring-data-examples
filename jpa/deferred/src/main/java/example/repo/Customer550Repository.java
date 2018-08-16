package example.repo;

import example.model.Customer550;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer550Repository extends CrudRepository<Customer550, Long> {

	List<Customer550> findByLastName(String lastName);
}
