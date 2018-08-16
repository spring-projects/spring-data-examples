package example.repo;

import example.model.Customer125;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer125Repository extends CrudRepository<Customer125, Long> {

	List<Customer125> findByLastName(String lastName);
}
