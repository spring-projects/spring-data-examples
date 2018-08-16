package example.repo;

import example.model.Customer720;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer720Repository extends CrudRepository<Customer720, Long> {

	List<Customer720> findByLastName(String lastName);
}
