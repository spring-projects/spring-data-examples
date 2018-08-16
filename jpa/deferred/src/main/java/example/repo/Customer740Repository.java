package example.repo;

import example.model.Customer740;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer740Repository extends CrudRepository<Customer740, Long> {

	List<Customer740> findByLastName(String lastName);
}
