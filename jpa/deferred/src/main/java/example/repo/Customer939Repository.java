package example.repo;

import example.model.Customer939;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer939Repository extends CrudRepository<Customer939, Long> {

	List<Customer939> findByLastName(String lastName);
}
