package example.repo;

import example.model.Customer716;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer716Repository extends CrudRepository<Customer716, Long> {

	List<Customer716> findByLastName(String lastName);
}
