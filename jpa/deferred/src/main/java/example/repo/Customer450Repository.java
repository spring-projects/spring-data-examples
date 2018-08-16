package example.repo;

import example.model.Customer450;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer450Repository extends CrudRepository<Customer450, Long> {

	List<Customer450> findByLastName(String lastName);
}
