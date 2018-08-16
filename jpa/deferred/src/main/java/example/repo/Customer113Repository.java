package example.repo;

import example.model.Customer113;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer113Repository extends CrudRepository<Customer113, Long> {

	List<Customer113> findByLastName(String lastName);
}
