package example.repo;

import example.model.Customer177;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer177Repository extends CrudRepository<Customer177, Long> {

	List<Customer177> findByLastName(String lastName);
}
