package example.repo;

import example.model.Customer786;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer786Repository extends CrudRepository<Customer786, Long> {

	List<Customer786> findByLastName(String lastName);
}
