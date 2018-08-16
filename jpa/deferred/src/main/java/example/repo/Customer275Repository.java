package example.repo;

import example.model.Customer275;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer275Repository extends CrudRepository<Customer275, Long> {

	List<Customer275> findByLastName(String lastName);
}
